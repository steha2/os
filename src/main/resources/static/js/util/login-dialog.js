function checkMove(action){
  $.get("/user/isLogin").done((isLogin)=>{
    if(isLogin === "true") {
      doAction(action);
    } else {
      openLoginDialog(action);
    }
  });
}

function openLoginDialog(action){
  const dialog = $("<div id='dialog-div' title='로그인'>")
  const inputCss = {width:240,padding:5,margin:5};
  const nameInput = $("<input type='text' placeholder='User Name'>").css(inputCss);
  const pwInput = $("<input type='password' placeholder='Password'>").css(inputCss);
  const remember = $("<input type='checkbox' id='remember'>")
  $("body").append(dialog);
  
  dialog.append(nameInput,"<br>",pwInput,"<br>",remember,"<label for='remember'>remember</label>",
   "<img src='/images/star.png' width='15' height='15' style='margin-left:80px;'>",
   " <a href='/user/signUpForm?isClose=true' target='_blank'>회원가입</a>");

  if ($.cookie("remember") === "true") {
    remember.prop("checked", true);
    nameInput.val($.cookie("name"));
    pwInput.val($.cookie("password"));
  }
  remember.change(() => {
    $.cookie("remember", remember.is(":checked"), { expires: 70 });
    if (!remember.is(":checked")) {
      $.removeCookie("name", { path: "/" });
      $.removeCookie("password", { path: "/" });
      $.removeCookie("remember", { path: "/" });
    }
  });

  dialog.dialog({
  autoOpen: false,
  height: 250,
  width: 300,
  modal: true,
  buttons: {
    "로그인": ()=>{ 
      const name = nameInput.val();
      const password = pwInput.val();
      if(name.length >= 3 && password.length >=3) {
        if (remember.is(":checked")) {
          $.cookie("name", name, { expires: 70 });
          $.cookie("password", password, { expires: 70 });
        }
        $.get("/user/auth",{name:name,password:password}).done(resData=>{
          if(resData) {
            alert("로그인 성공");
            doAction(action);
            dialog.dialog("close");
            dialog.remove();
          } else {
            alert("로그인 실패");
          }
        }).fail(()=>console.log("Ajax Error"));
      } else {
        alert("아이디, 비밀번호 3자 이상 입력");
      }
    },
    "닫기": () => (dialog.dialog("close"), dialog.remove())
  }})
  dialog.dialog("open");
} 

function doAction(action) {
  if(typeof action === "function"){
    action();
  } else if(action.length){
    location.href = action;
  }
}