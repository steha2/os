function runPay(orderVo,done){
  orderVo.pgName="신용카드";done();return;//forTest
  BootPay.request({
      price: orderVo.price, //실제 결제되는 가격
 
      // 관리자로그인 -> 결제설치 -> 인증키 및 보안 -> WEB Application ID
      application_id: "645b44843049c8001c9685ba",
 
      name: orderVo.name, //결제창에서 보여질 이름
      pg: orderVo.pg,
      method: orderVo.method, //결제수단, 입력하지 않으면 결제수단 선택부터 화면이 시작합니다.
      show_agree_window: 0, // 부트페이 정보 동의 창 보이기 여부
      items: orderVo.items
      // [
      //     {
      //         item_name: '나는 아이템', //상품명
      //         qty: 1, //수량
      //         unique: '123', //해당 상품을 구분짓는 primary key
      //         price: 1000, //상품 단가
      //     }
      // ]
      ,
      order_id: orderVo.id, //고유 주문번호로, 생성하신 값을 보내주셔야 합니다.
  }).error(function (data) {
      //결제 진행시 에러가 발생하면 수행됩니다.
      console.log(data);
  }).cancel(function (data) {
      //결제가 취소되면 수행됩니다.
      console.log(data);
  }).close(function (data) {
      // 결제창이 닫힐때 수행됩니다. (성공,실패,취소에 상관없이 모두 수행됨)
      console.log(data);
  }).done(function (data) {
      //결제가 정상적으로 완료되면 수행됩니다
      //비즈니스 로직을 수행하기 전에 결제 유효성 검증을 하시길 추천합니다.
      orderVo.pgName = data.pg_name;
      done();
      console.log("결제완료");
      console.log(data);
  });
}

function createOrderVo(items, orderSeq, totalPrice) {
  const orderVo = {
                   pg:"nicepay",
                   method:"card",
                   name:sliceText(items[0].name,15) + `[${items.length}] 건`,
                   id:orderSeq,
                   price:totalPrice
                  };
  orderVo.items = [];
  items.forEach(item => {
  orderVo.items.push({
              item_name:item.name,
              qty:item.amount,
              unique:"I"+item.id,
              price:item.dcPrice
            });
  });
  return orderVo;
}

function pay(items,totalPrice,done) {
  if(!Array.isArray(items)) items = [items];
  if(items.length !== 0 && confirm(totalPrice.toLocaleString()+"원 결제를 시작합니다.")){
    checkMove(()=>{
      $.get("/login/getOrderSeq").done((orderSeq)=>{
        const orderVo = createOrderVo(items,orderSeq,totalPrice);
        runPay(orderVo,()=>{
          orderVo.items = JSON.stringify(orderVo.items);
          $.post("/login/addOrder",orderVo).done(resData=>{
            console.log(resData);
            if(resData){
              alert("결제 완료");
              if(done) done();
              location.href = "/login/payComplate/"+orderVo.id;
            }
          });
        });
      })
    });
  }
}