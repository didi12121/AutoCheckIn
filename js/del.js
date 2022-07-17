var wb_url = "https://www.weibo.com/";
if(window.location.host == "weibo.com"){
    wb_url = "https://weibo.com/";
}

//删除本条微博
function del_weibo(id){
    console.log(id);
    var postdata = "mid="+id;
    delwb_url = wb_url  + "aj/mblog/del?ajwvr=6";
    fetch(delwb_url,
        {
            "credentials":"include",
            "headers":{
                "content-type":"application/x-www-form-urlencoded",
            },
            "body":postdata,
            "method":"POST","mode":"cors"
        }).then(response => console.log(response) )
        .then(data => console.log(data))
        .catch(error => console.log(error));
}

//删除本页全部微博
function del_page(){
    var wb_list = document.querySelectorAll(".S_txt2");
    if(wb_list.length == 0){
        console.log("暂无可删除微博");
    }
    var i = 1;
    for(var t of wb_list){
        if(t.name){
            //限制请求速度
            setTimeout(function(t) {
                del_weibo(t.name);
                var pppp_node = t.parentNode.parentNode.parentNode.parentNode;
                pppp_node.parentNode.removeChild(pppp_node);
            }, 200*i,t);
            i++;
        }
    }
    if(i == 1){
        console.log("暂无可删除微博:");
        // 正在加载中，请稍候...(滚动鼠标) #
        // 第几页，下一页（点击翻页） #
        //查看更早微博 #
    }
}


// 刷新微博页面
function auto_update_page(){
    var pages = document.querySelectorAll(".W_pages > a");
    if(pages.length > 0){
        var next_page = pages[pages.length-1];
        console.log("下一页");//or上一页...
        next_page.click();
    }else{
        //
        var more_arr = document.getElementsByClassName("WB_cardmore");
        if((more_arr.length != 0) && more_arr[more_arr.length-1].innerText.trim().startsWith("查看更早微博")){
            more_arr[more_arr.length-1].click();
        }
        var wait_load = document.getElementsByClassName("W_loading");
        if(wait_load.length != 0){
            console.log(new Date().toLocaleTimeString() + "：" + wait_load[0].parentNode.innerText.trim());
            window.scrollTo(0, 100000);//滚动到最低部（触发自动加载微博）
            setTimeout(function(){
                window.scrollTo(0, 100);
            },800);
        }
    }
}


function del_all_weibo(){
    if(window.del === false){
        return;
    }
    del_page();
    auto_update_page();//尝试自动刷新
}

if(document.URL.startsWith(wb_url)){
    var may_url = wb_url + $CONFIG.uid + "?is_all=1";
    console.log("如果微博未被删除，请点击下面链接重试：");
    console.log(may_url)
    // 定时执行删除
    window.setInterval(del_all_weibo,4000);
}else{
    alert("请在 " + wb_url +  " 站点下的微博列表中重新执行删除脚本。");
    if(navigator.userAgent.includes("Mobile")){
        alert("Chrome 用户请回到控制台，按下 Ctrl+Shift+M 切换回电脑版完整视图");
    }
    window.location.replace(wb_url);
}