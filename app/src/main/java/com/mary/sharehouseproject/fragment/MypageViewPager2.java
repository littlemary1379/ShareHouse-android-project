package com.mary.sharehouseproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mary.sharehouseproject.R;
import com.mary.sharehouseproject.activity.MainActivity;
import com.mary.sharehouseproject.model.User;

import org.w3c.dom.Text;


public class MypageViewPager2 extends Fragment {
    private static final String TAG = "MypageViewPager2";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private User user;

    private TextView tvEmail;
    private EditText etName,etPhone,etUpdatePw,etUpdatePw2;
    private RadioGroup genderRadioGroup;
    private RadioButton rdBtnMale,rdBtnFemale;
    private Button btnUpdate;
    private FirebaseUser firebaseUser;

    private String userEmail, documentId, updateName,updatePhone,updateGender,updatePw,updatePw2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.mypage_viewpage2,container,false);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        userEmail=firebaseUser.getEmail();

        init(v);
        listener();

        db.collection("user").whereEqualTo("email",userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                user=document.toObject(User.class);
                                Log.d(TAG, "onComplete: 유저 정보 "+user);
                                documentId=document.getId();
                                Log.d(TAG, "onComplete: id"+documentId);
                                UpdateUI(user);
                            }
                        }else{
                            Log.d(TAG, "onComplete: 실패");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 실패");
            }
        });


        return v;
    }

    private void init(View v){
        tvEmail=v.findViewById(R.id.tv_email);
        etName=v.findViewById(R.id.et_name);
        etPhone=v.findViewById(R.id.et_phone);
        genderRadioGroup=v.findViewById(R.id.rdGroup_gender);
        rdBtnMale=v.findViewById(R.id.rdBtn_male);
        rdBtnFemale=v.findViewById(R.id.rdBtn_female);
        btnUpdate=v.findViewById(R.id.btn_update);
        etUpdatePw=v.findViewById(R.id.et_update_pw);
        etUpdatePw2=v.findViewById(R.id.et_update_pw2);
    }

    private void UpdateUI(User user){
        tvEmail.setText(user.getEmail());
        etName.setText(user.getName());
        etPhone.setText(user.getPhone());
        if(user.getGender()!=null&&user.getGender().equals("남")){
            rdBtnMale.setChecked(true);
        }else if(user.getGender()!=null&&user.getGender().equals("여")){
            rdBtnFemale.setChecked(true);
        }
    }

    private void listener(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase(documentId);
                updatePassword();
            }
        });
    }

    private void updateDatabase(String documentId){
        updateName=etName.getText().toString();
        updatePhone=etPhone.getText().toString();
        if(rdBtnMale.isChecked()){
            updateGender="남";
        }else if(rdBtnFemale.isChecked()){
            updateGender="여";
        }

       DocumentReference ref=db.collection("user").document(documentId);
        ref
                .update(
                "name",updateName,
                        "phone",updatePhone,
                        "gender",updateGender
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: 업데이트에 성공하였습니다.");
                Toast.makeText(getContext(), "업데이트에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 업데이트에 실패하셨습니다");
                Toast.makeText(getContext(), "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }

    private void updatePassword(){
        updatePw=etUpdatePw.getText().toString();
        updatePw2=etUpdatePw2.getText().toString();
        if(updatePw==null||updatePw2==null||updatePw.equals("")||updatePw2.equals("")){
            return;
        }

        if(updatePw.equals(updatePw2)){
            firebaseUser.updatePassword(updatePw)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: 업뎃 성공");
                                Toast.makeText(getContext(), "비밀번호 업데이트 성공", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: 업데이트 실패");
                    Toast.makeText(getContext(), "업데이트에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
        }

    }
}
