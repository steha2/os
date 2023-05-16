class CommentInput {
  constructor(path, callBack, style = {},) {
    if (!path) { throw new Error("Path is null."); }
    this.path = path;
    this.callBack = callBack;
    this.width = style.width || 300;
    this.height = style.height || 100;
    this.div = $("<div>").css({
      width: this.width + "px",
      height: this.height + "px",
      border: "1px solid #ccc",
      padding: "10px",
      textAlign:"right"
    });
    this.textarea = $("<textarea maxlength='1000'>").css({
      width: "98%",
      marginTop:5,
      // padding:0,
      height: 60,
      resize: "none",
    });
    this.score = $(`<input type='number' min='0' max='5' value='5'>`).css({width:30,margin:5});
    this.commentButton = $("<button>").text("등록").click(this.addComment.bind(this));
    this.div.append(this.textarea, `<br><i class='fas fa-star' style='color: #f3da35'></i>`,
                    this.score,this.commentButton);
  }

  addComment(){
    const text = this.textarea.val();
    if(text){
      checkMove(()=>{ 
        $.post("/login/addComment/",{content:text,path:this.path,score:this.score.val()}).done((resData)=>{
          if(resData) {
            alert("등록 완료");
            if(this.callBack) this.callBack();
          }
        }).fail(()=>console.log("Ajax Fail"));
      })
    }
  }
}