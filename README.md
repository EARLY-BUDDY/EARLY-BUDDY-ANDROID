#  🐥 we are EARLY-BUDDY-ANDROID

### 얼리버디 - 약속시간을 위한 나만의 대중교통 배차 알리미

<img src="https://user-images.githubusercontent.com/37479631/71730558-d8bcc780-2e85-11ea-9e61-4a827e58558f.png" alt="img" style="width=100;" />

### workflow

![img](file:///Users/jinee/Desktop/KakaoTalk_Photo_2020-01-03-23-29-42.png?lastModify=1578063559)

## 1. 적용 라이브러리


    //리사이클러뷰 라이브러리
    implementation 'androidx.recyclerview:recyclerview:1.1.0-alpha06'
    
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
    
    //constraint Layout 사용을 위한 라이브러리
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    
    //Lottie Library
    implementation 'com.airbnb.android:lottie:3.2.2'
    
    //google map
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    
    // fcm - firebase를 이용해 알림 구현 라이브러리
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
   - select    : 자주 가는 장소 선택
 - route        : 세로 경로
 - schedule     : 일정
 - user         : 유저(로그인,회원가입)

 #### 3. network  : 통신

 #### 4. util : 애니메이션



## 3.주요 기능 구현 방법 (현재까지 진행한 사항)

### 0. 스플래쉬

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

  

### 1. 최초가입(로그인,회원가입,닉네임, 자주가는 장소 등록)

- TextWatcher  사용해서 예외처리 및 버튼활성화.
  ex) 중복확인, 특정문자 제한, 글자수 제한, 활성화 비활성화 버튼색상 변경

  - PlaceSearchActivity.kt

  ```
   act_place_search_et_search.addTextChangedListener(object : TextWatcher {
          override fun afterTextChanged(p0: Editable?) {
  
          }
  
          override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
  
          }
  
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //통신
                getPlaceSearch()
                Log.d("testtest", "onTextChanged")
            }
        })
  ```

- 회원가입

  - SignupActivity

  ```
  private fun passwordCheck() {
        act_signup_et_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if ((p0!!.length < 6) || !(pwdPattern.matcher(act_signup_et_pw.text.toString()).matches())) {
                    act_signup_tv_pw_ment.showOrInvisible(true)
    
                    act_signup_cl_pw.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                    act_signup_et_pw.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                    pwFlag = false
                } else {
                    act_signup_tv_pw_ment.showOrInvisible(false)
                    act_signup_cl_pw.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                    act_signup_et_pw.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )
    
                    if(!act_signup_et_pw.text.toString().equals(act_signup_et_pw_check.text.toString())) {
                        act_signup_tv_pw_check_ment.showOrInvisible(true)
                        act_signup_cl_pw_check.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                        act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                        pwCheckFlag = false
                    }
                    pwFlag = true
                }
            }
    
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    
            }
    
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    
            }
        })
    }
  
  ```



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

- when 을 통한 람다식 적용

```
when (holder.itemViewType) {
                    //지하철
                    1 -> {
                        holder.direction.text = String.format("%s 방면", routeList[position].way)
                        holder.startingText.text =
                            String.format("%s역", routeList[position].startName)
                        holder.endText.text = String.format("%s역", routeList[position].endName)

                    }
                    //버스
                    2 -> {
                        holder.ridingNumber.text =
                            String.format("%s", routeList[position].lane.busNo)
                        holder.startingText.text =
                            String.format("%s", routeList[position].startName)
                        holder.endText.text = String.format("%s", routeList[position].endName)
                        holder.direction.text = "방향을 주의하고 탑승하세요"
                    }
    
                }
```

- with 람다식 사용

```
//경로데이터 넣기
    fun setRouteItem(newRouteList: ArrayList<SubPath>) {
        with(routeList) {
            clear()
            addAll(newRouteList)
        }
        notifyDataSetChanged()
    }
```

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

```
private fun TextView.setAnimInt(value: Int) {
        startAnimation(TextViewIntAnimation(this, to = value))
    }
```


- 시간이 줄어드는 애니메이션 kotlin extension 을 이용하여 생성

```
   private fun TextView.start(token: Int) { //타이머 스타트
        var a = this
        if (token == 0) {
            timerTask = timer(period = 10) {
                // period = 10 0.01초 , period = 1000 면 1초
                time--

                val min = (time / 6000) % 60 // 1분
                runOnUiThread {
                    // Ui 를 갱신 시킴.
    
                    if (min < 10) { // 분
                        minmin = "$min"
                    } else {
                        minmin = "$min"
                    }
    
                    a.text = String.format("%s", minmin)
                    if (Integer.valueOf(minmin) <= 3) {
                        act_home_tv_minute_number.visibility = View.INVISIBLE
                        act_home_tv_before_minute.visibility = View.INVISIBLE
                        act_home_tv_soon.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
```

### 5. 일정표

- 커스텀 달력을 통한 일정 조회 및 등록

- 오늘 날짜에 파란 마커 디폴트

- 커스텀 달력 -> 뷰페이저와 리사이클러뷰 이용

### 6. 맵 띄우기

- google map api 적용

- 경도 위도 좌표를 받아 구글맵에 16F zoom 으로 화면 표시

### 7. 홈 화면

- 가장 최근에 있는 일정에 맞춰 화면 적용

- 일정에 대한 대중교통 알림이 시작되면 1대,2대,3대 이동중 에 따라 화면 변환

- 대중교통 도착이 임박(3분 이내)가 되면 곧 도착 이라는 문구 표시
- 다음 도착 대중교통 정보가 없으면 그 정보를 지우고 밑으로 붙여야하는데 여기서 ConstraintLayout의 서로 연결되는 속성을 사용해서 잘 해결함.

```
 var bottomParams = act_home_cl_middle_bar.layoutParams  as? ConstraintLayout.LayoutParams
                            bottomParams?.topMargin = 90
                            act_home_cl_middle_bar.layoutParams = bottomParams
viewGone()
```

```
private fun viewGone() {
        act_home_tv_bus_number.visibility = View.GONE
        act_home_tv_bus_current_location.visibility = View.GONE
        act_home_tv_next_bus.visibility = View.GONE
        act_home_tv_next_bus_var.visibility = View.GONE
        act_homme_iv_reboot.visibility = View.GONE
    }
```



### 8. 일정 등록

- datePicker와 timePicker로 날짜와 시간 선택

```
act_schedule_tv_date_click.setOnClickListener {
            DatePickerDialog(this@ScheduleActivity, R.style.MyDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    act_schedule_tv_date_click.text = SimpleDateFormat("yyyy.MM.dd").format(cal.time)

                },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
```

- 장소 검색을 통해 받은 출발지와 도착지 좌표로 가로경로 표시

```

```
