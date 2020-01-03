#  🐥 we are EARLY-BUDDY-ANDROID

## 1. 적용 라이브러리


    //리사이클러뷰 라이브러리
    implementation 'androidx.recyclerview:recyclerview:1.1.0-alpha06'
    
    //동그란 이미지 커스텀 뷰 라이브러리 : https://github.com/hdodenhof/CircleImageView
    implementation 'de.hdodenhof:circleimageview:3.0.1'
    
    //Retrofit 라이브러리 : https://github.com/square/retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    //Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위해
    implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'
    
    //객체 시리얼라이즈를 위한 Gson 라이브러리 : https://github.com/google/gson
    implementation 'com.google.code.gson:gson:2.8.6'
    //Retrofit 에서 Gson 을 사용하기 위한 라이브러리
    implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
    
    //이미지 로드를 위해 glide 라이브러리 : https://github.com/bumptech/glide
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    
    //ODsay api : 대중교통 api 
    implementation 'com.google.code.findbugs:jsr305:2.0.1'
    
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    
    //카카오맵 라이브러리 적용
    implementation files('libs\\libDaumMapAndroid.jar')


    //FloationgActionButton
    implementation 'com.github.clans:fab:1.6.4'
    
    //Lottie Library
    implementation 'com.airbnb.android:lottie:3.2.2'
    
    //google map
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    
    implementation 'com.google.firebase:firebase-core:16.0.6'	// 애널리틱스(기본)
    implementation 'com.google.firebase:firebase-messaging:17.3.4'	// 클라우드 메시징

## 2. 프로그램 구조

 data,feature,network,util

 #### 1. data

 - calendar     : 달력
 - db           : sharedPreference
 - place        : 주소
 - route        : 경로
 - schedule     : 일정
 - user         : 회원

 #### 2. feature

 - calendar     : 달력
 - home         : 홈 화면
 - initial_join : 최초가입
 - intercepter  : header 추가 intercepter
 - place        : 장소
   - search    : 장소 검색
   - select    : 장소 선택
 - route        : 세로 경로
 - schedule     : 일정
 - user         : 유저(로그인,회원가입)

 #### 3. network  : 통신

 #### 4. util : 애니메이션



## 3.주요 기능 구현 방법 (현재까지 진행한 사항)

### 0. 스플래쉬 & 자동로그인

- Lottie 애니메이션 적용 

  - activity_splash.xml

  ```
  <com.airbnb.lottie.LottieAnimationView
          android:id="@+id/act_splash_av"
          android:layout_width="0dp"
          android:layout_height="0dp"
          android:scaleType="fitXY"
          app:layout_constraintBottom_toBottomOf="parent"
          app:layout_constraintEnd_toEndOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintTop_toTopOf="parent"
          app:lottie_autoPlay="true"
          app:lottie_fileName="splash.json"/>
  ```

  - SplashActivity.kt

  ```
  Handler().postDelayed({
              //아이디 있을 경우. 자동로그인인 경우
              if (id.isNotEmpty()) {
                  Log.d("test", "id : "+ id) //자동로그인 됨
                  //통신
                  if(nickName.isNotEmpty()){
                      //홈
                      goToHomeActivity()
                      finish()
                  }else{ //닉네임 설정
                      goToSetNickNameActivity()
                      finish()
                  }
              }
              //아이디 없을 경우. 자동로그인 아닌 경우
              else{//회원가입
                  goToSigninActivity()
                  finish()
              }
          }, SPLASH_TIME_OUT)
  ```

  

### 1. 최초가입(로그인,회원가입,닉네임, 자주가는 장소 등록)

- TextWatcher  사용해서 예외처리 및 버튼활성화.
  ex) 중복확인, 특정문자 제한, 글자수 제한, 활성화 비활성화 버튼색상 변경

- SharedPreference로 자동로그인 구현

- ReCyclerView로 장소 검색 통신 전 더미데이터

- Custom dialog를 직접 구현하여 사용자가 선택한 사항을 뷰에 반영

- ConstraintLayout안에 TextView, EditText 구현

- kotlin extension을 이용하여 layout 파일의 객체의 참조 얻어오기

- kotlin의 람다식을 사용한 click 이벤트 구현

- 하나라도 입력하지 않은 항목이 있을 경우 Toast 메시지 띄우기

### 2. 세로 경로

- 출발지에서 목적지로 가는 세로 경로를 보여주는 뷰 제작. 경유 정류장 표시하기 위해 리사이클러뷰 안에 리사이클러뷰 제작

- 지하철 경로(18개) 와 버스 경로(10개) 의 다양한 경우의 수로 인한 internal constructor 생성

- internal constructor 로 인한 코드 줄임

- 세로 경로 뷰 서버와의 retroifit 라이브러리를 이용해  통신 완료

- 두개의 data class 를 받는 두개의 adapter,viewHolder 생성


### 3. 가로경로

#### 소요시간을 bar로 보여주는 가로경로 뷰 그리기

<타야하는 교통 수단의 갯수와 소요 시간에 따라 동적으로 달라지는 기능>

- nine-patch를 이용해 경로 이미지에서 늘리고 싶은 부분만 선택하여 조절 가능

- LinearLayout에 horizental로 경로 바를 배치하고 weight로 길이 조정

- backgroundTint로 각 경로 바의 색깔을 해당 호선의 색깔로 변경

- 가로 경로 뷰 서버와의 retroifit 라이브러리를 이용해  통신 완료

  - Item_list_place_search_route.xml

  ```
  <RelativeLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="20dp">
  
          <RelativeLayout
              android:id="@+id/act_schedule_route_rl_gray"
              android:layout_width="match_parent"
              android:layout_height="wrap_content">
              <ImageView
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:background="@drawable/img_gray_line"/>
  
          </RelativeLayout>
  
          <LinearLayout
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:layout_marginHorizontal="18dp"
              android:orientation="horizontal">
  
              <RelativeLayout
                  android:id="@+id/act_schedule_route_rl_walk_1"
                  android:layout_width="0dp"
                  android:layout_height="wrap_content"
                  android:layout_weight="3"
                  >
                  <ImageView
                      android:layout_width="match_parent"
                      android:layout_height="wrap_content"/>
              </RelativeLayout>
  						.
  						.
  						.
              <RelativeLayout
                  android:id="@+id/act_schedule_route_rl_walk_4"
                  android:layout_width="0dp"
                  android:layout_height="wrap_content"
                  android:layout_weight="2">
                  <ImageView
                      android:layout_width="match_parent"
                      android:layout_height="wrap_content"/>
              </RelativeLayout>
              
          </LinearLayout>
      </RelativeLayout>
  ```

  

### 4. 애니메이션

- 숫자 올라가는 애니메이션 kotlin extension 을 이용하여 생성

### 5. 일정표

### 6. 맵 띄우기

- google map api 적용

- 경도 위도 좌표를 받아 구글맵에 16F zoom 으로 화면 표시

### 7. 홈 화면

- 가장 최근에 있는 일정에 맞춰 화면 적용

- 일정에 대한 대중교통 알림이 시작되면 1대,2대,3대 이동중 에 따라 화면 변환

- 

### 8. 일정 등록

- datePicker와 timePicker로 날짜와 시간 선택

- 장소 검색을 통해 받은 출발지와 도착지 좌표로 가로경로 표시
