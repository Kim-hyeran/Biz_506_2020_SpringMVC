$(function () {
    // 화면에 scroll이 발생하면
    $(window).scroll(function () {
        let headerHeight = $("header").height(); // header tag의 높이 값
        let windowTop = $(window).scrollTop(); // 화면을 세로 방향으로 스크롤할 때 윈도우 화면의 최상단 좌표 가져오기

        if (windowTop > headerHeight) {
            $("#main-nav").css("position", "fixed");
            $("#main-nav").css("top", "0");
        } else {
            $("#main-nav").css("position", "relative");
        }

        console.log(headerHeight, windowTop);
    });

    $("li#menu-home").click(function () { })
    $("li#menu-books").click(function () { })
    $("li#menu-read-book").click(function () { })
    $("li#menu-login").click(function () { })

    // nav의 li tag를 클릭했을 때
    $("#main-nav li").click(function () {
        let menu_text = $(this).text();
        let menu_id = $(this).attr("id");
        if (menu_text === "도서정보") {
            document.location.href = `${rootPath}/books`;
        } else if (menu_id === "menu-home") {
            document.location.href = `${rootPath}/`;
        } else if (menu_id === "menu-read-book") {
            document.location.href = `${rootPath}/read/`;
        } else if (menu_id === "menu-join") {
            document.location.href = `${rootPath}/member/join`;
        } else if (menu_id === "menu-mypage") {
            document.location.href = `${rootPath}/member/mypage`;
        } else if (menu_id === "menu-login") {
            // localhost:8080/book/login
            // spring security에서 자체 지원되는 login form을 사용(아래 실행 코드)
            document.location.href = `${rootPath}/member/login`;
        } else if (menu_id === "menu-logout") {
            $.ajax({
                url: `${rootPath}/logout`,
                method: "POST",
                // spring security logout을 수행할 때 post 방식으로 요청을 해야 한다.
                // post 방식으로 ajax 요청을 할 때 보안 경고가 뜨고, 서버가 거부하는 현상 발생
                // spring security server는 post 방식으로 요청할 때 csrf_token 값을 건네주어야 한다.
                // spring form tag를 사용할 때는 문제가 없지만 ajax 방식으로 요청하면 수동으로 설정을 해야 한다.
                // 그 때 사용하는 속성이 beforeSend이다.
                // beforeSend에 Callback 함수를 붙여서 setRequestHeader에 csrf 속성 값들을 설정하여 전달해주어야 한다.
                beforeSend: function (ax) {
                    ax.setRequestHeader(`${csrf_header}`, `${csrf_token}`)
                },
                success: function (result) {
                    document.location.replace(`${rootPath}/`)
                },
                error: function () {
                    alert("서버와 통신 오류 발생")
                }
            })
        }
    });
});