	//1. ** 페이징처리 관련 변수선언
	//9. ** 댓글목록 관련 : function selectData(pageNum){}
	//10. ** 페이지처리 이벤트 연결(다음댓글 보기 버튼 클릭시, 데이터 추가) : $('paging-button input').click(function(){}
	//5. ** 댓글 등록 : $('#re_form').submit(function(event){}	//아직목록작업을 하지않았기때문에, 정상적으로 등록되었는지 확인은 sql에서 
		//6. BoardDAO - 댓글갯수( getReplyBoardCount() )
		//7. BoardDAO - 댓글목록( getListReplyBoard() ) 
		//8. <Board>ListReplyAction
	//4. ** 댓글 작성 폼 초기화 : function initForm(){}
	//3. ** textarea에 내용 입력시 글자수 체크 : $(document).on('keyup','textarea',function(){}
	//13. ** 댓글 수정버튼 클릭시 수정폼 노출 : $(document).on('click','.modify-btn',function(){}
	//** 수정폼에서 취소버튼 클릭시 수정폼 초기화 (취소버튼은 댓글이 생성된 후에 생기는 미래의 태그 => document.on) : $(document).on('click','.re-reset',function(){}
	//		
	//** 댓글 수정폼 초기화 : function initModifyForm(){}
	 	
	//** 댓글 수정 : $(document).on('submit','#mre_form',function(event){}
		//11. BoardDAO - 댓글상세 ( getReplyBoard() )
		//12. BoardDAO - 댓글 수정 ( updateReplyBoard() )
	//** 댓글 삭제 : 댓글이 생성되어야 삭제버튼이 보임 = 미래의 태그 => document.on : $(document).on('click','.delete-btn',function(){}
	//2. ** 초기 데이터(목록) 호출 : selectData(1);

	$(function(){ 	
	//1. ** 페이징처리 관련변수 선언
	let currentPage;
	let count;
	let rowCount;
	
	
	//9. ** 댓글목록 관련 : function selectData(pageNum){}
 function selectData(pageNum){	//pageNum을 넘겨주면 currentPage로 지정
		//* 현재 페이지 지정 (for, paging처리)
		currentPage = pageNum;
		
		//* 로딩이미지 노출
		$('#loading').show();	
	
		//* ajax통신
		$.ajax({
			type:'post',		  //item_num은 form에 hidden으로 들어가있음
			data:{pageNum:pageNum, item_num:$('#item_num').val()},	//pageNum의 데이터와 item_num의 데이터를 넘겨줌
			url:'chattingList.do',
			dataType:'json',
			cache:false,
			timeout:3000,
			success:function(param){
				//* 로딩이미지 감추기
				$('#loading').hide();
				
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum==1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){	
					//출력하고 싶은 문자열을 모두 담아둘 output이라는 변수(?)를 만듦
					let output = '<div class="item">';	 
					output += '<h4>' + 'ID : '+ item.mem_id +'</h4>';
					output += '<div class = "sub-item">';
					output += '<p>' + item.content + '</p>';		//'댓글내용' 표시
					output += '<span class="modify-date">작성일 : ' + item.reg_date + '</span>';

					output += '<hr size="1" noshade width="100%">';					
					output += '</div>';
					output += '</div>';
					
					//* output에 담긴 데이터를 문서객체에 추가
					$('#output').append(output);
				});
				
				//* each메서드를 빠져나온 다음 -> page button처리 연산 
				//(버튼의 생성시기 : 버튼을눌러야 페이지처리가능하기때문에, 버튼이 언제 생성되는지를 처리해야한다. )
				if(currentPage>=Math.ceil(count/rowCount)){
					//true(currentPage가 같거나 더 클 때) = 다음페이지가 없음
					$('.paging-button').hide();
				}else{
					//false = 다음페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				//json형식에 맞지않는 데이터가 들어온다거나, 네트워크 오류가 발생한 경우
				alert('네트워크 오류발생!'); //=> 발생시에는 이클립스의 콘솔 에러메시지를 보기
			}			
		});
	}	
	
	//10. ** 페이지처리 이벤트 연결(다음댓글 보기 버튼 클릭시, 데이터 추가) : $('paging-button input').click(function(){}
	$('.paging-button input').click(function(){
		selectData(currentPage + 1);
	});		
	
	//5. ** 댓글 등록 : $('#re_form').submit(function(event){}	//아직목록작업을 하지않았기때문에, 정상적으로 등록되었는지 확인은 sql에서 
	$('#re_form').submit(function(event){			
			
		//* 내용이 비어있을 경우	
		if($('#content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#content').val().focus();
			return false;
		}	
		//* form이하의 태그에 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//* 데이터전송(by.ajax통신)
		$.ajax({
			url:'writeChat.do',
			type:'post',
			data:form_data,
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//* 폼초기화
					initForm();
					alert('전송완료');
					//* 댓글작성이 성공하면 새로 입력한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출함.
					selectData(1);
				}else{
					alert('등록시 오류발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!');
			}
		});
		//* 기본이벤트 제거
		event.preventDefault();
	});	
		//6. BoardDAO - 댓글갯수( getReplyBoardCount() )
		//7. BoardDAO - 댓글목록( getListReplyBoard() ) 
		//8. <Board>ListReplyAction
		
		
	//4. ** 댓글 작성 폼 초기화 : function initForm(){}
	function initForm(){
		$('textarea').val('');
		//$('#re_first .letter-count').text('300/300');
	}	
	
	//3. ** textarea에 내용 입력시 글자수 체크 : $(document).on('keyup','textarea',function(){}


	
	//2. ** 초기 데이터(목록) 호출
	selectData(1);	//1페이지 목록정보를 읽어옴	(가장 최신글 목록정보를 읽어오는지 확인)
	
	});