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
			type:'post',		  //board_num은 form에 hidden으로 들어가있음
			data:{pageNum:pageNum, item_num:$('#item_num').val()},	//pageNum의 데이터와 item_num의 데이터를 넘겨줌
			url:'listReply.do',
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
					output += '<h4>' + item.id + '</h4>';			//'id'표시
					output += '<div class = "sub-item">';
					output += '<p>' + item.re_content + '</p>';		//'댓글내용' 표시
					
					if(item.re_modifydate){	//최근 수정일이 있을경우 '최근 수정일'데이터를 출력 / 최근 수정일이 없을경우 '등록일' 데이터를 출력
						output += '<span class="modify-date">최근 수정일 : ' + item.re_modifydate + '</span>';
					}else{
						output += '<span class="modify-date">등록일 : ' + item.re_date + '</span>';
					}
					
					//* 로그인한 회원번호와 작성자의 회원번호가 일치하는지 확인
					if(param.user_num == item.mem_num){
						//로그인한 회원번호와 작성자 회원번호가 일치한다면
						output += ' <input type="button" data-renum="'+item.re_num+'" value="수정" class="modify-btn">';
						output += ' <input type="button" data-renum="'+item.re_num+'" value="삭제" class="delete-btn">';
							//item.re_num은 custom데이터 속성임. data-renum에 댓글작성번호를 넣어서, 해당데이터로 수정 및 삭제를 편리하게 할 수 있다.
							//작성한 댓글 수정삭제하려면 item.re_num을 받아서 처리함.
					}
					
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
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#re_content').val().focus();
			return false;
		}	
		//* form이하의 태그에 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//* 데이터전송(by.ajax통신)
		$.ajax({
			url:'writeReply.do',
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


	//4. ** 댓글 작성 폼 초기화 : function initForm(){}
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}	

	
	//3. ** textarea에 내용 입력시 글자수 체크 : $(document).on('keyup','textarea',function(){}
	$(document).on('keyup','textarea',function(){										
		//* 입력한 글자수를 구함
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){	//* 300자를 넘어선 경우 => susbstring으로 문자열 잘라냄
			$(this).val($(this).val().substring(0,300));
			
		}else{	//* 300자 이하인 경우 => 몇자남았는지 알려줌 (ex. 100/300)
			let remain = 300 - inputLength;
			remain += '/300';
			
			//* 등록폼 글자수 체크	
			if($(this).attr('id')=='re_content'){	//textarea에 들어있는 id와 일치여부 확인
				$('#re_first .letter-count').text(remain);
			}else{	//수정폼 글자수 체크
				$('#mre_first .letter-count').text(remain);
			}
		}
	});	
	
	
	//13. ** 댓글 수정버튼 클릭시 수정폼 노출 : $(document).on('click','.modify-btn',function(){}
	$(document).on('click','.modify-btn',function(){	//수정버튼을 눌렀을때 발생하는 이벤트
		//* 댓글번호
		let re_num = $(this).attr('data-renum');
		
		//버튼의 태그는 output += '<p>' + item.re_content + '</p>'; ==> p태그이다.
		//p태그를 찾을 때는 부모태그와 함께 명시해야한다.
		//.parent().find('p') : 부모태그(sub-item)에 접근해서 'p'태그를 찾아라
		//* 댓글 내용										g:지정문자열 모두 찾아라. i:대소문자 무시
		let content = $(this).parent().find('p').html().replace(/<br>/gi, '\n');
													//br태그 대소문자 무시하고 모두 찾아서 \n으로 바꿔라	
													//이렇게하지않으면 <br>태그가 그대로 명시됨
		//* 댓글 수정폼 UI
		let modifyUI = '<form id="mre_form">';
			modifyUI += '  <input type="hidden" name="re_num" id="mre_num" value="' + re_num + '">';	//name과 id의 값이 다름
			modifyUI += '  <textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">'+ content +'</textarea>';
			modifyUI += '  <div id="mre_first"><span class="letter-count">300/300</span></div>';
			modifyUI += '  <div id="mre_second" class="align-center">';
			modifyUI += '	  <input type="submit" value="수정">';
			modifyUI += '	  <input type="button" value="취소" class="re-reset">';
			modifyUI += '  </div>';
			modifyUI += '  <hr size="1" noshade width="96%">';
			modifyUI += '</form>';
		
		//* 이미 수정중인 댓글이 있을경우(수정버튼을 눌렀을 경우), 다른 수정버튼을 눌렀을 때 이전에 누른 수정버튼은 숨김
		//sub-item을 환원시키고, 수정폼을 초기화함
		initModifyForm();
		
		//* 지금 클릭해서 수정하고자 하는 데이터는 감추기
		//수정버튼을 감싸고 있는 div(sub-item)을 감추기
		$(this).parent().hide();	
		//* 수정폼을 수정하고자 하는 데이터가 있는 div에 노출(hide시켰으니까 우리가 만든 form을 붙여줘야함)
		$(this).parents('.item').append(modifyUI);	//버튼의 여러부모들 중에서 원하는 부모(item)를 지정 ???? 2/10,09:37,00:33
		
		//* 입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		
		//* 문서객체에 반영
		$('#mre_first .letter-count').text(remain);
	});	
	
	
	//** 수정폼에서 취소버튼 클릭시 수정폼 초기화 (취소버튼은 댓글이 생성된 후에 생기는 미래의 태그 => document.on) : $(document).on('click','.re-reset',function(){}
	$(document).on('click','.re-reset',function(){
		initModifyForm();		
	});			
			
			
	//** 댓글 수정폼 초기화 : function initModifyForm(){}
	function initModifyForm(){
		$('.sub-item').show();	//안보여지고있던걸 보여지게 함
		$('#mre_form').remove(); //form에서는 id를 명시하고잇었기때문에, hide시키면안되고 아예 remove시켜야한다.(id가 중복될 수 있기 때문)
	};	 	
	
	
	//** 댓글 수정 : $(document).on('submit','#mre_form',function(event){}
/*submit 이벤트가 발생하면 action에 명시한대로 페이지가 이동하지만,액션을 명시하지도 않았고 페이지 이동하면 안된다.
 *( 이벤트를 제거하지 않는이상 )submit이벤트 발생 시 기본이벤트가 있기 떄문에 액션을 명시하지않을 경우에는 자기자신을 호출
 *그래서 하단에 event.preventDefault()로 기본이벤트 제거함
 */
	$(document).on('submit','#mre_form',function(event){ //event핸들러로 이벤트 객체를 받고 하단에서 기본이벤트 제거
		//아무것도 입력안하고 수정누를수 있으므로 조건체크
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#re_content').val('').focus();
			return false; //기본이벤트 제거
		}
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize(); //serialize()이용해서 쿼리문자열로 해서 한번에 다읽어올 수 있는 메서드
		//서버와 통신
		$.ajax({
			url:'updateReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
/*
 * 방법1- 수정댓글 작성하고 수정누르면 그게 서버로 날아가서 저장시켜서 저장된데이터를 다시읽어서 표시하기(한건의 데이터 읽어오기)
 * 방법2- 수정댓글 작성한 데이터가 저장되니까 화면에 보여지는(수정댓글 작성한) 데이터를 표시한다.
 */				
				//방법2로 처리해보자 (위에서 정상적으로 처리되면, 화면에 보여지는(수정한) 데이터를 표시한다.)
				if(param.result=='logout'){
					alert('로그인 해야 수정할 수 있습니다');
				}else if(param.result=='success'){
/*
 * 폼에넣을때 br태그를\n으로 바꿨었으므로 태그에 붙일때는 다시 br태그로 바꿔놔야한다. 
 * 그래야 정상적으로 표시됨. 202라인참조 (방법2를 사용하기때문)
 */						
					//부모태그로올라가서 p태그 find하고 거기에 넣어준다.
					$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'<br>'));
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 방금 전');
					
					//데이터처리가 됐으니, 수정폼 삭제 및 초기화
					initModifyForm();
					
				}else if(param.result=='wrongAccess'){
					alert('잘못된 접근. 타인의 글을 수정할 수 없습니다')
				}else{
					alert('수정 시 오류 발생')
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});	
		//기본이벤트 제거
		event.preventDefault(); //맨위에서 event객체를 받고 기본이벤트제거	
	});


	//** 댓글 삭제 : 댓글이 생성되어야 삭제버튼이 보임 = 미래의 태그 => document.on : $(document).on('click','.delete-btn',function(){}
	
	$(document).on('click','.delete-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		
		$.ajax({
			url:'deleteReply.do',
			type:'post',
			data:{re_num:re_num},//{key:value(위에 변수)}
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result =='logout'){
					alert('로그인해야 삭제할 수 있습니다')
				}else if(param.result=='success'){
					alert('삭제 완료');
					selectData(1);
				}else if(param.result=='wrongAccess'){
					alert('타인의 글을 삭제할 수 없습니다.')
				}else{
					alert('삭제 시 오류 발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!')
			}
		});
	});
	
	
	//2. ** 초기 데이터(목록) 호출
	selectData(1);	//1페이지 목록정보를 읽어옴
	
	});