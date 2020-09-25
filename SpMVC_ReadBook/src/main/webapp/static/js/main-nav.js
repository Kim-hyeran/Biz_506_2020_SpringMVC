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
        }
    });
});