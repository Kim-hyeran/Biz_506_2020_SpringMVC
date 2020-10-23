# 사진 이미지를 업로드하여 이미지 갤러리 BBS로 변환
 * spring framework에서는 기본적으로 Text 위주의 project만 지원한다.
 * file(종류에 관계 없이)을 upload하기 위해서 apache에서 지원하는 dependency의 도움을 받아야 한다.
	- commons-io, commons-fileupload

### file을 upload할 수 있도록 서버 context 설정
 * file-context.xml 파일을 설정하여 file upload 설정
 * context-param에서 핸들링할 수 있도록 root-context.xml과 같은 위치에 작성
 * file 선택을 하기 위해 input type="file" tag를 추가
 * form tag에 enctype="multipart/form-data" 추가
 * upload하는 파일의 type을 제한하고 싶을 때 accept="image/*"로 설정
	- 파일의 확장자를 검색하여 업로드 파일을 제한하고 싶을 때 : accept=".gif, .jpg, .jpeg, .png"
 	- media type과 확장자를 검사하여 제한하고 싶을 때 : accept="video/mp4, audio/mp3, image/png"
 
### 게시물에서 첨부파일 CRUD 구현
1. insert
	- 파일을 업로드할 때 파일 이름 UUID 부착하기
	- 파일에 UUID를 부착하여 upload를 실행하고, UUID가 부차고딘 파일 이름을 게시글의 file명 필드에 추가한 후 insert 수행

2. delete
	- 업로드된 첨부파일을 삭제하는 작업 우선 수행
	- seq 값으로 게시글을 가져오고, file명 필드에서 첨부파일 이름을 getter하여 upload 폴더의 파일 삭제
	- 해당 게시물 삭제
	- 순서대로 수행하지 않으면 게시물은 없는데 주인 없는 파일들이 upload 폴더에 쌓이게 된다.

3. update
	- seq 값을 게시물에 가져와서 write.jsp에 보이고, 변경할 데이터를 입력 받기
	- 파일이 첨부가 되면 기존의 파일을 삭제하고 새로운 파일을 upload, 새로 upload된 파일 이름을 다시 file명 필드에 저장 후 update 수행
	- 파일이 첨부되지 않았으면 기존의 file명 필드에 저장된 값이 변경되지 않도록 하면서 update 수행

### 다중 파일 업로드 CRUD 구현
 1. 다중 파일 업로드
 2. 파일의 원래 이름과 업로드 된 파일 이름 정보를 List로 생성
 3. bbs table에 데이터를 insert
 4. bbs_table로부터 새로 생성된 seq값 가져오기
 5. file table에 파일 list를 insert할 때, i_b_seq 칼럼에 bbs의 seq 값을 같이 첨부하여 insert