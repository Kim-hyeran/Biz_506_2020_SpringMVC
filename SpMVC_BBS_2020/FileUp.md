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