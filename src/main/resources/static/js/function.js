
function hasCoda(value) {
    return ((value.charCodeAt(value.length - 1) - 0xAC00) % 28) > 0;
}



function isValid(target, fieldName, focusTarget) {
    if (target.value.trim()) {
        return true;
    }

    const particle = (hasCoda(fieldName)) ? '을' : '를'; // 조사
    const elementType = (target.type === 'text' || target.type === 'password' || target.type === 'search' || target.type === 'textarea') ? '입력' : '선택';
    alert( `${fieldName + particle} ${elementType}해 주세요.` );

    target.value = '';
    ( !focusTarget ? target : focusTarget).focus();
    throw new Error(`"${target.id}" is required...`)
}


    function getJson(uri,params) {

        let json = {};

        $.ajax({
            url : uri,
            type : 'get',
            dataType : 'json',
            data : params,
            async : false,
            success : function (response) {
                console.log("성공");
                json = response;
                console.log(json);
            },
            error : function (request, status, error) {
                console.log(error)
                console.log("실패");
            }
        })

        return json;
    }


    function callApi(uri, method, params) {
        var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
        let json = {};
         JSON.stringify(params);
         $.ajax({
            url : uri,
            type : method,
               xhrFields: {
                    withCredentials: true
                },
            contentType : 'application/json; charset=utf-8',
            dataType : 'json',
            data : (params) ? JSON.stringify(params) : {},
            beforeSend : function(xhr)
                        {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            				xhr.setRequestHeader(header, token);
                        },
            async : false,
            success : function (response) {
                json = response;
            },
            error : function (request, status, error) {
                console.log(error)
            }
        })
        console.log(json);
        return json;
    }
