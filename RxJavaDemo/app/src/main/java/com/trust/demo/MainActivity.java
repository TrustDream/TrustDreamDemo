package com.trust.demo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding2.view.RxView;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button logShuZuBtn ,idImgBtn , takePicturesBtn,dialogBtn,bluetoothBtn
            ,intentBluetoothBtn;
    ImageView imgIdImg , glideImg , glideCircle,glideRoundedCorners,gliderCircleGif
            ,gliderRoundedCornersGif,glideMoHu,glideMoHuGif,takePicturesImg;
    ImageButton imgBtn;
    private String TAG = "lhh";
    Bitmap   photo;

    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 100;

    //bluetooch
    private BluetoothManager blueToothManger ;
    private BluetoothAdapter blueToothAdapter;

    private String serviceUUID ,characteristicUUID ,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持4.0", Toast.LENGTH_SHORT).show();
            finish();
        }else
        {
            Toast.makeText(this, "支持4.0", Toast.LENGTH_SHORT).show();
        }

        //监听电池电量
        this.registerReceiver(this.broadcastReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));


        verifyStoragePermissions(this);

        initBlooth();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initBlooth() {


        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
//判断是否需要 向用户解释，为什么要申请该权限
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
            }
        }






        blueToothManger = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//        blueToothAdapter = blueToothManger.getAdapter();
        blueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(blueToothAdapter == null)
        {
            Log.d(TAG, "initBlooth: yous phone is no Bluetooth!");
            return;
        }
        if(!blueToothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,1);
        }
    }



    private void initView() {
        logShuZuBtn = (Button) findViewById(R.id.log_shu_zu);
        idImgBtn = (Button) findViewById(R.id.img_id);
        idImgBtn.setOnClickListener(this);
        logShuZuBtn.setOnClickListener(this);

        glideImg = (ImageView) findViewById(R.id.glide_img);
        Glide.with(MainActivity.this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489645419596&di=5b8e3677f9c66f282d86e6e1dbff87f8&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3D74ce30ebac51f3dec3b2b96ca4eff0ec%2Fbf803101213fb80e923499f635d12f2eb8389490.jpg")
                .asGif().error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(glideImg);
        imgBtn = (ImageButton) findViewById(R.id.glide_img_img);
        imgBtn.setOnClickListener(this);

        //圆图
        glideCircle = (ImageView) findViewById(R.id.glide_img_circle);
        Glide.with(this)
                .load("http://img3.91.com/uploads/allimg/140331/32-140331164952.jpg")
                .bitmapTransform(new CropCircleTransformation(this))
                .into(glideCircle);
        //圆角
        glideRoundedCorners = (ImageView) findViewById(R.id.glide_img_rounded_corners);
        Glide.with(this)
                .load("http://img3.91.com/uploads/allimg/140331/32-140331164952.jpg")
                .crossFade(1000)
                .bitmapTransform(new RoundedCornersTransformation(this
                        ,30,0,RoundedCornersTransformation.CornerType.TOP))
                .into(glideRoundedCorners);


        //圆图gif
        gliderCircleGif = (ImageView) findViewById(R.id.glide_img_circle_gif);
        Glide.with(this)
                .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=352230896,438188533&fm=23&gp=0.jpg")
                .bitmapTransform(new CropCircleTransformation(this))
                .into(gliderCircleGif);
        //圆角gif
        gliderRoundedCornersGif = (ImageView) findViewById(R.id.glide_img_rounded_corners_gif);
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489660506304&di=87ebc0319462da1ab292ac28ae46bc6e&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Dcf60dc8cac4bd11304cdb73a6aada488%2Ffc0874d98d1001e93ef42cf0b90e7bec56e7974c.jpg")
                .bitmapTransform(new RoundedCornersTransformation(this
                        ,30,0,RoundedCornersTransformation.CornerType.ALL))
                .into(gliderRoundedCornersGif);

        //高斯模糊
        glideMoHu = (ImageView) findViewById(R.id.glide_img_mo_hu);
        Glide.with(this)
                .load("http://img3.91.com/uploads/allimg/140331/32-140331164952.jpg")
                .bitmapTransform(new BlurTransformation(this,23,1))
                .into(glideMoHu);

        //高斯模糊gif
        glideMoHuGif = (ImageView) findViewById(R.id.glide_img_mo_hu_gif);
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489660506304&di=87ebc0319462da1ab292ac28ae46bc6e&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Dcf60dc8cac4bd11304cdb73a6aada488%2Ffc0874d98d1001e93ef42cf0b90e7bec56e7974c.jpg")
                .bitmapTransform(new BlurTransformation(this,23,1))
                .into(glideMoHu);


        //rxbinding
        Button rxBinding = (Button) findViewById(R.id.rxbinding);
        RxView.clicks(rxBinding).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "rxbing  onclick");
            }
        });

        Button fangZhiLianXuDianJi = (Button) findViewById(R.id.rxbing_fang_zhi_liuanxudianji);
        RxView.clicks(fangZhiLianXuDianJi)
                .throttleFirst(3,TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "防止连续点击");
            }
        });


        //10s后 打印log

        Observable
                .fromArray(100)
                .subscribeOn(Schedulers.io())
                .timer(10*1000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ObservableTransformer<Object, Object>() {

                    @Override
                    public ObservableSource<Object> apply(Observable<Object> upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "延时打印的日志"+o.toString());
            }
        });


        //合并  输出
        List<String> ml1 = new ArrayList<>();
        ml1.add("1");
        ml1.add("2");
        ml1.add("3");
        ml1.add("4");
        ml1.add("5");

        List<String> ml2 = new ArrayList<>();
        ml2.add("a");
        ml2.add("b");
        ml2.add("c");
        ml2.add("d");
        ml2.add("e");
        ml2.add("f");
        ml2.add("g");

         Observable  observable1=Observable.fromArray(ml1);
        Observable  observable2=Observable.fromArray(ml2);


        Observable.zip(observable1, observable2, new BiFunction() {
            @Override
            public Object apply(@NonNull Object o, @NonNull Object o2) throws Exception {
                return o;
            }
        }).subscribe(new Consumer() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "合并失败 zip  不会用  :" + o);
            }
        });

        //list中数据一个一个显示
        List<Integer> mli3 = new ArrayList<>();
        mli3.add(1);
        mli3.add(2);
        mli3.add(3);
        Flowable.just(mli3)
                .flatMap(new Function<List<Integer>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(@NonNull List<Integer> integers) throws Exception {
                        return Flowable.fromIterable(integers);
                    }
                }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "只修改list中一个数据" + o.toString());
            }
        });

        //rxjava 2  转换符  map
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Object, Object>() {
            @Override
            public Object apply(@NonNull Object o) throws Exception {
                return "String +"+o;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "rxjava2  转换符 map  int->String :" + o);
            }
        });




        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            long millionSeconds = sdf.parse("19940806000000").getTime();//毫秒
            Log.d(TAG, "initView: "+millionSeconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //get
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
        GitHubInterface gitHubInterface  =retrofit.create(GitHubInterface.class);

        Call<ResponseBody> call = gitHubInterface.contributorsBySimpleGetCall("users","stven0king","repos");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String json = response.body().toString();

//                Log.d("MainActivity onResponse ", json);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MainActivity onFailure", t.toString());
            }
        });


        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(),new TrustAllCerts())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier());
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Content-Type","application/json")
                        .addHeader("Accept","application/json")
                        .addHeader("token","Bearer k9tKrdfSW7fmpYSPbjw7lobThCo+NX8dKU6NXyUVew1Z4MVCS5xe6m9ynlX2iPc+Z8Pq9Wuf9Mjr9Xgpouf8BQ==")
                        .build();
                return chain.proceed(request);

            }
        });

        OkHttpClient client = okHttpClient.build();

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("https://180.168.194.98:8443/EBWebServer-2.0/")
                .client(client)
                .build();

        GitHubInterface gitHubInterfaces  = retrofit1.create(GitHubInterface.class);

        Map<String,Object> map = new WeakHashMap<>();
        map.put("termId",860337030343524L);
        JSONObject json = new JSONObject(map);


        for (int i = 0; i <10 ; i++) {

            RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json.toString());

            Call<ResponseBody> calls = gitHubInterfaces.postResult(body);
            calls.enqueue(callback);
        }

        Map<String,Object> maps = new WeakHashMap<>();
        maps.put("one","1");
        maps.put("two","this");
        maps.put("there",1);
        maps.put("four",12121211211213212L);
        maps.put("six",1212121.10F);

        JSONObject jsonObject = new JSONObject(maps);
        Log.d(TAG, "jsonObject:"+jsonObject);



        //flatMap     转换后的结果是无序的

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Object o) throws Exception {
                List<String> ml = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    ml.add("this is ml +"+o);
                }
                return Observable.fromIterable(ml).delay(10,TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "flatMap    结果是无序的  : "+o);
            }
        });

        //concatMap   输出结果是有序的
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Object o) throws Exception {
                List<String> ml = new ArrayList<String>();
                for (int i = 0; i <3 ; i++) {
                    ml.add("concatMap 输出结果是有序的 +"+o);
                }
                return Observable.fromIterable(ml).delay(10,TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Log.d(TAG, "concatMap : "+o);
            }
        });



        takePicturesBtn = (Button) findViewById(R.id.takePictures);
        takePicturesBtn.setOnClickListener(this);
        takePicturesImg = (ImageView) findViewById(R.id.takePicturesImg);


        //dialog
        dialogBtn = (Button) findViewById(R.id.dialog);
        dialogBtn.setOnClickListener(this);
        //bluetooth
        bluetoothBtn = (Button) findViewById(R.id.bluetooch);
        bluetoothBtn.setOnClickListener(this);
        intentBluetoothBtn = (Button) findViewById(R.id.intent_bluetooth);
        intentBluetoothBtn.setOnClickListener(this);

    }

    public void test(View v)
    {

        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> e) throws Exception {
                    e.onNext("Hello World   3");
                    Thread.sleep(5000);
                    e.onNext("Hello World   2");
                    Thread.sleep(1000);
                    e.onNext("Hello World   1");

                    e.onComplete();
                }
            });
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
        List<Integer> ml = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
                ml.add(i);
        }
        Observable.fromArray(ml).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Integer> integers) {
            if(integers != null)
            {
                for (int i = 0; i <integers.size() ; i++) {
                    Log.d("MainActivity", "List:" + integers.get(i)+"|thread name:"+Thread.currentThread().getName());
                }
            }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });





    }


    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.d("MainActivity", "onNext:" + s+"|thread name:"+Thread.currentThread().getName());
        }

        @Override
        public void onError(Throwable e) {
            Log.d("MainActivity", "onError:" + e);
        }

        @Override
        public void onComplete() {
            Log.d("MainActivity", "onComplete:" );
        }
    };


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.log_shu_zu:
                String [] name = {"trust","me","lh"};
                Observable.fromArray(name).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d("MainActivity", "name:" + s);
                    }
                });
                break;
            case R.id.img_id:
                final int id = R.mipmap.ic_launcher;

                imgIdImg = (ImageView) findViewById(R.id.img_id_img);

                Observable.create(new ObservableOnSubscribe<Object>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        Drawable drawable = getTheme().getDrawable(id);
                        e.onNext(drawable);
                        e.onComplete();
                    }
                }).subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d("MainActivity", "onNext:" + o.toString());
                        imgIdImg.setImageDrawable((Drawable) o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MainActivity", "error:" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;

            case R.id.glide_img_img:
                Glide.with(this).
                        load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1965368099,4241684755&fm=21&gp=0.jpg").
                        asGif()
                        .error(R.mipmap.ic_launcher).
                        fallback(R.drawable.arrow_right_blue).
                        placeholder(R.drawable.e_bike)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imgBtn);
                break;


            case R.id.takePictures:

                String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
                if (state.equals(Environment.MEDIA_MOUNTED)){   //如果可用
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri imageUri = Uri.fromFile(getImgFile());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,1);
                }else {
                    Toast.makeText(MainActivity.this,"sdcard不可用",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.dialog:
                if(photo!= null)
                {
                    AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();
                    builder.show();
                    builder.getWindow().setContentView(R.layout.dialog);
                    ImageView imageView = (ImageView) builder.findViewById(R.id.dialog_img);
//                    Glide.with(MainActivity.this).load(uri).asBitmap().into(imageView);
//                        imageView.setImageBitmap(photo);
                    builder.getWindow();
                }else
                {
                    Toast.makeText(this, "photo 为null", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bluetooch:
                doBluetooch();
                break;
            case R.id.intent_bluetooth:
                obtain();
                break;
        }
    }
    public void obtain(){


        BluetoothGattService service = blueToothGatt.getService(UUID.
                fromString(serviceUUID));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID
                .fromString(characteristicUUID));

        characteristic.setValue(new byte[]{(byte) 0x128, (byte) 0x110, (byte) 0x163});
//        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        blueToothGatt.writeCharacteristic(characteristic);
        blueToothGatt.readCharacteristic(characteristic);
        Log.d(TAG, "obtain  writeCharacteristic: "+blueToothGatt.writeCharacteristic(characteristic));
        Log.d(TAG, "obtain  readCharacteristic: "+blueToothGatt.readCharacteristic(characteristic));


        BluetoothGattDescriptor descriptor = characteristic.
                getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        blueToothGatt.writeDescriptor(descriptor);
    }

    private void verifyStoragePermissions(Activity activity) {
        int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                    if (getImgFile().exists()) {
                        FileInputStream fis = null;
                        try {
                            String path = getImgFile().getPath();
                            fis = new FileInputStream(path);
                            Bitmap bitmap = BitmapFactory.decodeStream(fis);
                            Log.d(TAG, "onActivityResult: bitmap1:"+bitmap.toString()
                            +"|bitmap 大小:"+(bitmap.getByteCount() / 1024 / 1024)+"m");


                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
                            byte[] bytes=baos.toByteArray();

                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            Log.d(TAG, "onActivityResult: bitmap:"+bitmap.toString()
                                    +"|bitmap2 大小:"+(bitmap.getByteCount() / 1024 / 1024)+"m");

                            Glide.with(this)
                                    .load(path)
                                    .into(takePicturesImg);






//                            takePicturesImg.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onActivityResult: e :"+e.toString());
                        }

                    }

//        Log.d(TAG, "onActivityResult: requestCode"+requestCode+"|resultCode:"+resultCode+"|data"+data.toString());
        if(resultCode == 0)
        {
            Toast.makeText(this, "你拒绝了 bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }




                        /*
                    //两种方式 获取拍好的图片
                    if (data.getData() != null || data.getExtras() != null) { //防止没有返回结果
                        Uri uri = data.getData();
                        if (uri != null) {

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 1;
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(uri.getPath(),options);

                            options.inJustDecodeBounds =false;
                            options.inDither =false;

                            photo = BitmapFactory.decodeFile(uri.getPath(),options);

                            photo  =BitmapFactory.decodeFile(uri.getPath()); //拿到图片

                        }
//                        Log.d(TAG, "BitmapFactory   onActivityResult: "+photo.toString());
//                        Glide.with(MainActivity.this).load(photo).asBitmap().into(takePicturesImg);
                        if (photo == null) {
                            Bundle bundle = data.getExtras();
                            if (bundle != null) {
                                photo = (Bitmap) bundle.get("data");
//                                takePicturesImg.setImageBitmap(photo);
                                Glide.with(MainActivity.this).load(photo).asBitmap().into(takePicturesImg);
                            } else {
                                Toast.makeText(getApplicationContext(), "找不到图片", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    */



    }

    private void doBluetooch() {
        /*
        查询已经配对的设备数量
         */
        List<String> blueToothNameList = new ArrayList<>();

        Set<BluetoothDevice> paireDevices =  blueToothAdapter.getBondedDevices();
        Log.d(TAG, "doBluetooch: paireDevices,size = "+paireDevices.size());
        if(paireDevices.size() >0)
        {
            for (BluetoothDevice device : paireDevices){
                blueToothNameList.add("name:"+device.getName());
                Log.d(TAG, "doBluetooch: 已经配对的设备名称:"+device.getName()+"|address:"+
                device.getAddress());

                blueToothAdapter.cancelDiscovery();
                address = device.getAddress();
                BluetoothDevice b = blueToothAdapter.getRemoteDevice(address);
                blueToothGatt = b.connectGatt(MainActivity.this,false,bluetoothGattCallback);
            }
        }else{
            Log.d(TAG, "doBluetooch: paireDevices,size = 0  没有已经配对的设备!");
            foundBluetooth();
        }



    }

    private void foundBluetooth() {
        Log.d(TAG, "foundBluetooth:");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(blueTooth,filter);
        blueToothAdapter.startDiscovery();

    }

    public final BroadcastReceiver blueTooth = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action =  intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device =  intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                Log.d(TAG, "onReceive  found bluetooth  device name :"+device.getName()+"|address:"
                        +device.getAddress());
                Log.d("lhhh", "onReceive: "+name);
                /*
                if(device.getName().equals("小米note（QR）")){
                    blueToothAdapter.cancelDiscovery();
                    address = device.getAddress();
                    BluetoothDevice b = blueToothAdapter.getRemoteDevice(address);
                    blueToothGatt = b.connectGatt(MainActivity.this,false,bluetoothGattCallback);
                }
                */
                if(name.equals("Galaxy A7 (2016)"))
                {
                    blueToothAdapter.cancelDiscovery();
                    address = device.getAddress();
                    BluetoothDevice b = blueToothAdapter.getRemoteDevice(address);
                    blueToothGatt = b.connectGatt(MainActivity.this,false,bluetoothGattCallback);
                }
            }
        }
    };
    BluetoothGatt blueToothGatt;
    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static UUID UUID_NOTIFY =
            UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE =
            UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";

    public BluetoothGattCharacteristic mNotifyCharacteristic;
    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            Log.i(TAG, "onConnectionStateChange :oldStatus=" + status + " NewStates=" + newState);
            String intentAction;
            if(status == BluetoothGatt.GATT_SUCCESS)
            {

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    intentAction = ACTION_GATT_CONNECTED;

                    broadcastUpdate(intentAction);
                    Log.i(TAG, "Connected to GATT server.");
                    // Attempts to discover services after successful connection.
                    Log.i(TAG, "Attempting to start service discovery:");
                    blueToothGatt.discoverServices();
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    intentAction = ACTION_GATT_DISCONNECTED;
                    blueToothGatt.close();
                    blueToothGatt = null;
                    Log.i(TAG, "Disconnected from GATT server.");
                    broadcastUpdate(intentAction);
                }
            }

        }

        private void broadcastUpdate(final String action) {
            final Intent intent = new Intent(action);
            sendBroadcast(intent);
        }

        public void findService(List<BluetoothGattService> gattServices)
        {
            Log.i(TAG, "Count is:" + gattServices.size());
            for (BluetoothGattService gattService : gattServices)
            {
//                00001801-0000-1000-8000-00805f9b34fb


//                Log.i(TAG, "UUID_SERVICE:"+UUID_SERVICE.toString());
//                if(gattService.getUuid().toString().equalsIgnoreCase(UUID_SERVICE.toString()))
//                {
                    List<BluetoothGattCharacteristic> gattCharacteristics =
                            gattService.getCharacteristics();
                    Log.i(TAG, "Count is:" + gattCharacteristics.size());
                    for (BluetoothGattCharacteristic gattCharacteristic :
                            gattCharacteristics)
                    {
//                        if(gattCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_NOTIFY.toString()))
//                        {
                            Log.i(TAG, "gattService.getUuid():"+gattService.getUuid().toString());
                            Log.i(TAG, "gattCharacteristic.getUuid().toString():"+gattCharacteristic.getUuid().toString());
//                            Log.i(TAG, "UUID_NOTIFY.toString()"+UUID_NOTIFY.toString());
                            serviceUUID = gattService.getUuid().toString();
                            characteristicUUID = gattCharacteristic.getUuid().toString();

                            mNotifyCharacteristic = gattCharacteristic;
                            setCharacteristicNotification(gattCharacteristic, true);

                            broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);


                        BluetoothGattService service = blueToothGatt.getService(UUID.
                                fromString("this is service"));
                        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID
                                .fromString("this is characteristic"));


                        characteristic.setValue(new byte[]{(byte) 0x128, (byte) 0x110, (byte) 0x163});
//        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                        blueToothGatt.writeCharacteristic(characteristic);
//        blueToothGatt.readCharacteristic(characteristic);

                        Log.d(TAG, "obtain  writeCharacteristic: "+blueToothGatt.writeCharacteristic(characteristic));
//        Log.d(TAG, "obtain  readCharacteristic: "+blueToothGatt.readCharacteristic(characteristic));

                            return;
//                        }
//                    }
                }
            }
        }



        public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                                  boolean enabled) {
            if (blueToothAdapter == null || blueToothGatt == null) {
                Log.d(TAG, "BluetoothAdapter not initialized");
                return;
            }
            blueToothGatt.setCharacteristicNotification(characteristic, enabled);
/*
        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
        */
            for(BluetoothGattDescriptor dp:characteristic.getDescriptors()) {
                dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                blueToothGatt.writeDescriptor(dp);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d(TAG, "onServicesDiscovered: ");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "onServicesDiscovered received: " + status);
                findService(gatt.getServices());
            } else {
                if(blueToothGatt.getDevice().getUuids() == null)
                    Log.d(TAG, "onServicesDiscovered received: " + status);
            }
            super.onServicesDiscovered(gatt, status);
        }

        private void broadcastUpdate(final String action,
                                     final BluetoothGattCharacteristic characteristic) {
            final Intent intent = new Intent(action);

            // This is special handling for the Heart Rate Measurement profile.  Data parsing is
            // carried out as per profile specifications:
            // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                //final StringBuilder stringBuilder = new StringBuilder(data.length);
                //for(byte byteChar : data)
                //    stringBuilder.append(String.format("%02X ", byteChar));
                //intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
                intent.putExtra(EXTRA_DATA, new String(data));
            }
            sendBroadcast(intent);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.d(TAG, "onCharacteristicRead: ");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.d(TAG, "onCharacteristicWrite: ");

            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.d(TAG, "onCharacteristicChanged: ");
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.d(TAG, "onDescriptorRead: ");
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.d(TAG, "onDescriptorWrite: ");
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            Log.d(TAG, "onReliableWriteCompleted: ");
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            Log.d(TAG, "onReadRemoteRssi: ");
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            Log.d(TAG, "onMtuChanged: ");
            super.onMtuChanged(gatt, mtu, status);
        }
    };



    /**
     * 设置文件存储路径，返回一个file
     * @return
     */
    private File getImgFile(){
        File file = new File(Environment.getExternalStorageDirectory()+"/com.coder/karl");
        if (!file.exists()){
            //要点！
            file.mkdirs();
        }
        File imgFile = new File(file,"karl.jpg");
        return imgFile;

    }



    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int levelPercent = (int)(((float)level / scale) * 100);

//            Log.d(TAG, "onReceive: 电池电量:"+levelPercent+"%");
//
//            Log.d(TAG, "onReceive: bluetooth:"+intent.toString());

            final String action = intent.getAction();
            if (ACTION_GATT_CONNECTED.equals(action)) {


                invalidateOptionsMenu();
            } else if (ACTION_GATT_DISCONNECTED.equals(action)) {


                invalidateOptionsMenu();

            } else if (
                    ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the
                // user interface.
//                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }

        }
    };

    /**
     *截图
     * @param activity
     * @return
     */
    public Bitmap captureScreen(Activity activity) {
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp=getWindow().getDecorView().getDrawingCache();
        return bmp;
    }


    public Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String json  =  response.body().string();
                if(json!= null)
                {
                    Log.d(TAG, "onResponse: json:"+json);
                }else {
                    Log.d(TAG, "onResponse: code:"+response.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d(TAG, "onFailure: t:"+t.toString());
        }
    };


}



