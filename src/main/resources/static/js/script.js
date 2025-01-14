(function($) {

    "use strict";

    var searchPopup = function() {
      // open search box
      $('#header-nav').on('click', '.search-button', function(e) {
        $('.search-popup').toggleClass('is-visible');
      });

      $('#header-nav').on('click', '.btn-close-search', function(e) {
        $('.search-popup').toggleClass('is-visible');
      });
      
      $(".search-popup-trigger").on("click", function(b) {
          b.preventDefault();
          $(".search-popup").addClass("is-visible"),
          setTimeout(function() {
              $(".search-popup").find("#search-popup").focus()
          }, 350)
      }),
      $(".search-popup").on("click", function(b) {
          ($(b.target).is(".search-popup-close") || $(b.target).is(".search-popup-close svg") || $(b.target).is(".search-popup-close path") || $(b.target).is(".search-popup")) && (b.preventDefault(),
          $(this).removeClass("is-visible"))
      }),
      $(document).keyup(function(b) {
          "27" === b.which && $(".search-popup").removeClass("is-visible")
      })
    }

    var countdownTimer = function() {
      function getTimeRemaining(endtime) {
        const total = Date.parse(endtime) - Date.parse(new Date());
        const seconds = Math.floor((total / 1000) % 60);
        const minutes = Math.floor((total / 1000 / 60) % 60);
        const hours = Math.floor((total / (1000 * 60 * 60)) % 24);
        const days = Math.floor(total / (1000 * 60 * 60 * 24));
        return {
          total,
          days,
          hours,
          minutes,
          seconds
        };
      }
  
      function initializeClock(id, endtime) {
        const clock = document.getElementById(id);
        const daysSpan = clock.querySelector('.days');
        const hoursSpan = clock.querySelector('.hours');
        const minutesSpan = clock.querySelector('.minutes');
        const secondsSpan = clock.querySelector('.seconds');
  
        function updateClock() {
          const t = getTimeRemaining(endtime);
          daysSpan.innerHTML = t.days;
          hoursSpan.innerHTML = ('0' + t.hours).slice(-2);
          minutesSpan.innerHTML = ('0' + t.minutes).slice(-2);
          secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);
          if (t.total <= 0) {
            clearInterval(timeinterval);
          }
        }
        updateClock();
        const timeinterval = setInterval(updateClock, 1000);
      }
  
      $('#countdown-clock').each(function(){
        const deadline = new Date(Date.parse(new Date()) + 28 * 24 * 60 * 60 * 1000);
        initializeClock('countdown-clock', deadline);
      });
    }

    var initProductQty = function(){

      $('.product-qty').each(function(){

        var $el_product = $(this);
        var quantity = 0;

        $el_product.find('.quantity-right-plus').click(function(e){
            e.preventDefault();
            var quantity = parseInt($el_product.find('#quantity').val());
            $el_product.find('#quantity').val(quantity + 1);
        });

        $el_product.find('.quantity-left-minus').click(function(e){
            e.preventDefault();
            var quantity = parseInt($el_product.find('#quantity').val());
            if(quantity>0){
              $el_product.find('#quantity').val(quantity - 1);
            }
        });

      });

    }

  var resetSearchField = function() {
    // Search submit button 클릭 시 input 초기화
    document.querySelector('.search-submit').addEventListener('click', function(e) {
      e.preventDefault(); // 기본 제출 동작 방지
      const searchInput = document.querySelector('#search-form')

      searchInput.form.submit();

      searchInput.value = ''; // input 필드 초기화
    });
  };

    $(document).ready(function() {

      searchPopup();
      resetSearchField();
      initProductQty();
      countdownTimer();

      /* Video */
      var $videoSrc;  
        $('.play-btn').click(function() {
          $videoSrc = $(this).data( "src" );
        });

        $('#myModal').on('shown.bs.modal', function (e) {

        $("#video").attr('src',$videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0" ); 
      })

      $('#myModal').on('hide.bs.modal', function (e) {
        $("#video").attr('src',$videoSrc); 
      })

      var mainSwiper = new Swiper(".main-swiper", {
        speed: 500,
        navigation: {
          nextEl: ".main-slider-button-next",
          prevEl: ".main-slider-button-prev",
        },
      });

      var productSwiper = new Swiper(".product-swiper", {
        spaceBetween: 20,        
        navigation: {
          nextEl: ".product-slider-button-next",
          prevEl: ".product-slider-button-prev",
        },
        breakpoints: {
          0: {
            slidesPerView: 1,
          },
          660: {
            slidesPerView: 3,
          },
          980: {
            slidesPerView: 4,
          },
          1500: {
            slidesPerView: 5,
          }
        },
      });      

      var testimonialSwiper = new Swiper(".testimonial-swiper", {
        slidesPerView: 1,
        spaceBetween: 20,
        navigation: {
          nextEl: ".testimonial-button-next",
          prevEl: ".testimonial-button-prev",
        },
      });

      var thumb_slider = new Swiper(".thumb-swiper", {
        slidesPerView: 1,
      });
      var large_slider = new Swiper(".large-swiper", {
        spaceBetween: 10,
        effect: 'fade',
        thumbs: {
          swiper: thumb_slider,
        },
      });

    }); // End of a document ready

    window.addEventListener("load", function () {
      const preloader = document.getElementById("preloader");
      preloader.classList.add("hide-preloader");
    });

})(jQuery);

// ---------------------



// 로그인 폼
document.getElementById('loginForm').addEventListener('submit', function(event) {
  event.preventDefault(); // 폼 제출을 방지

  // 로그인 시도
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const rememberMe = document.getElementById('remember-me').checked ? 'true' : 'false';  // remember-me 체크박스 값

  const formData = new FormData();
  formData.append('username', username);
  formData.append('password', password);
  formData.append('remember-me', rememberMe)


  // AJAX로 로그인 요청
  fetch('/login', {
    method: 'POST',
    body: formData
  })
      .then(response => {
        if (response.ok) {
          alert('로그인 성공!')

          window.location.href = `/index?remember-me=${rememberMe}`;
        } else {
          // 로그인 실패 (응답 코드 401)
          return response.json();
        }
      })
      .then(data => {
          console.log('errorMessage');
        if (data && data.error) {
          // 로그인 실패 시 오류 메시지 모달에 표시
          const errorMessageElement = document.getElementById('loginError');
          errorMessageElement.style.display = "block";  // 에러 메시지 표시
          errorMessageElement.textContent = data.error;

          if (data.code === "CustomAccountExpiredException"){
            const form = document.createElement('form');
            form.method = 'GET';
            form.action = "/users/dormant";

            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'userId';
            input.value = data.userId;

            form.appendChild(input);

            const button = document.createElement('button');
            button.type = 'submit';
            button.textContent = '휴면 유저 해제하기';
            button.classList.add('btn');
            button.classList.add('btn-danger')
            form.appendChild(button);

            errorMessageElement.appendChild(form);
          }
        }
      })
      .catch(error => {
        console.error('Login failed:', error);
      });
});

// 회원가입 폼
document.getElementById('registerForm').addEventListener('submit', function(event) {
  event.preventDefault(); // 폼 제출을 방지

  // 로그인 시도
  const form = document.getElementById('registerForm');

  const loginId = form.querySelector('input[name="loginId"]').value;
  const password = form.querySelector('input[name="password"]').value;
  const username = form.querySelector('input[name="userName"]').value;
  const email = form.querySelector('input[name="email"]').value;
  const mobileNumber = form.querySelector('input[name="mobileNumber"]').value;
  const birth = form.querySelector('input[name="birth"]').value;
  const gender = form.querySelector('input[name="gender"]:checked').value;


  const formData = new FormData();

  formData.append('loginId', loginId);
  formData.append('password', password);
  formData.append('userName', username);
  formData.append('email', email);
  formData.append('mobileNumber', mobileNumber);
  formData.append('birth', birth);
  formData.append('gender', gender);

  // AJAX로 로그인 요청
  fetch('/users/localUser/register', {
    method: 'POST',
    body: formData
  })
      .then(response => {
        if (response.ok) {
          alert('회원가입 성공!')
          window.location.reload();
        } else {
          // 회원가입 실패 (응답 코드 409)
          return response.json();
        }
      })
      .then(data => {
        if (data && data.error) {
          // 로그인 실패 시 오류 메시지 모달에 표시
          const errorMessageElement = document.getElementById('registerError');
          errorMessageElement.textContent = data.error;
          errorMessageElement.style.display = "block";  // 에러 메시지 표시
        }
      })
      .catch(error => {
        console.error('register failed:', error);
      });
});


const urlParams = new URLSearchParams(window.location.search);
const showModal = urlParams.get('showLoginModal');

if (showModal === 'true'){
  new bootstrap.Modal(document.getElementById('authModal')).show()
}
