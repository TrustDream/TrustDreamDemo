package com.trust.demo.code.gsonparsing;

import android.os.Bundle;
import android.view.View;

import com.trust.demo.BaseActivity;
import com.trust.demo.R;
import com.trust.demo.code.gsonparsing.entity.Common;
import com.trust.demo.code.gsonparsing.entity.CommonList;
import com.trust.demo.code.gsonparsing.entity.FromJsonUtils;
import com.trust.demo.code.gsonparsing.entity.Person;
import com.trust.demo.code.gsonparsing.entity.User;
import com.trust.demo.tool.L;

public class GsonParsingActivity extends BaseActivity {
    private String CommonJson ="{\"status\":\"ok\",\"message\":\"登陆成功\",\"data\":\"\"}";
    private String CommonUserJson ="{\"status\":\"ok\",\"message\":\"登陆成功\",\"data\":{\"id\":\"lh\",\"age\":\"15\",\"sex\":\"女\"}}";
    private String CommonPersonJson ="{\"status\":\"ok\",\"message\":\"登陆成功\",\"data\":{\"name\":\"吕小芳\",\"user\":{\"id\":\"1\",\"age\":\"15\",\"sex\":\"女\"}}}";
    private String CommonPersonSJson ="{\"status\":\"ok\",\"message\":\"登陆成功\",\"data\":{\"name\":\"吕小芳\",\"list\":[{\"id\":\"1\",\"age\":\"15\",\"sex\":\"女\"},{\"id\":\"1\",\"age\":\"15\",\"sex\":\"男\"}]}}";
    private String CommonListUserJson ="{\"status\":\"ok\",\"message\":\"登陆成功\",\"data\":[{\"id\":\"1\",\"age\":\"15\",\"sex\":\"女\"},{\"id\":\"1\",\"age\":\"15\",\"sex\":\"男\"}]}";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_parsing);

    }

    public void Common(View view){
        Common result=new FromJsonUtils(Object.class, CommonJson).fromJson();
        L.d("Common:"+"state:"+result.getStatus()+"        message:"+result.getMessage());
    }

    public void CommonUser(View view){
        Common<User> result1=new FromJsonUtils(User.class, CommonUserJson).fromJson();
        L.d("CommonUser:"+"state:"+result1.getStatus()+"message:"+result1.getMessage()
                +"data:"+"sex>"+result1.getData().getSex()+"name:"+result1.getData().getId());
    }
    public void CommonPerson(View view){
        Common<Person> result2=new FromJsonUtils(Person.class, CommonPersonJson).fromJson();
        L.d("CommonPerson:"+"state:"+result2.getStatus()+"        message:"+result2.
                getMessage()+"data:"+"name>"+result2.getData().getName()+"      sex>"+result2.getData().getUser().getSex());
    }

    public void CommonPersonS(View view){
        Common<Person> result3=new FromJsonUtils(Person.class, CommonPersonSJson).fromJson();
        L.d("CommonPersonS:"+"state:"+result3.getStatus()+"message:"+result3.getMessage()+"data:"+"name>"+
                result3.getData().getName()+"      sex>"+result3.getData().getList().get(1).getSex());
    }


    public void CommonListUser(View view){
        CommonList<User> list=new FromJsonUtils(User.class, CommonListUserJson).fromJsonList();
        L.d("CommonListUser:"+"data>>"+list.getData().toString());
    }


}
