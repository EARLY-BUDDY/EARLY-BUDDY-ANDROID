# EARLY-BUDDY-ANDROID

## 1.적용 라이브러리


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
   
## 2.프로그램 구조
  
 data,feature,network 
 
 #### 1. data
 - calendar     : 달력
 - login        : 회원
 - place        : 주소
 - route        : 경로
 
 #### 2. feature
 - calendar     : 달력
 - home         : 홈 화면
 - initial_join : 최초가입
 - place        : 장소
   - search    : 장소 검색
   - select    : 장소 선택
 - route        : 세로 경로
 - schedule     : 일정
 - user         : 유저(로그인,회원가입)
 - intercepter  : header 추가 intercepter
 - util         : 애니메이션
 
 #### 3. network  : 통신

## 3.주요 기능 구현 방법


### 1. 최초가입(로그인,회원가입)
- TextWatcher  사용해서 예외처리 및 버튼활성화.
ex) 중복확인, 특정문자 제한, 글자수 제한, 버튼색상 변경 -회원가입, 닉네임설정

- SharedPreference로 자동로그인 구현

- ReCyclerView로 장소 검색 통신 전 더미데이터

- Custom dialog를 직접 구현하여 사용자가 선택한 사항을 뷰에 반영

- ConstraintLayout안에 TextView, EditText 구현


### 2. 세로 경로
- 출발지에서 목적지로 가는 세로 경로를 보여주는 뷰 제작. 경유 정류장 표시하기 위해 리사이클러뷰 안에 리사이클러뷰 제작.

- 지하철 경로(약 15개) 와 버스 경로(4개) 를 나타내기 위해 많은 분기를 사용. (람다식 사용)

- 세로 경로 뷰 서버와의 retroifit 라이브러리를 이용해  통신 완료



### 3. 가로경로

#### 소요시간을 bar로 보여주는 가로경로 뷰 그리기

<타야하는 교통 수단의 갯수와 소요 시간에 따라 동적으로 달라지는 기능>

- 각 경로 별 이미지를 ConstraintLayout 안의 ConstraintLayout으로 작성

- 전체 시간과 경로 별 소요 시간을 이용해 전체 길이에서 해당 경로가 차지하는 비율을 구하는 수식 작성

- nine-patch를 이용해 경로 이미지에서 늘리고 싶은 부분의 길이 조정

- 비율 계산식을 통해 해당 경로의 시작 위치를 구하고 marginStart를 조정하여 배치

  
### 4. 애니메이션
- 숫자 올라가는 애니메이션 kotlin extension 을 이용하여 생성.
