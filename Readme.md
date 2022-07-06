# 如何自动打卡超话

1. ## 获取cookie

    1. 运行jar包前须确保电脑安装了chrome，以及对应版本的 [chromeDriver](https://chromedriver.chromium.org/downloads/version-selection)
    2. java -jar 运行cookie文件夹下的jar包然后微博扫码登录获取cookie
    3. 将获取到的cookie输入到**application.yml**中的**wb.cookie**中

2. ## 获取pid（超话的id）

    1. 在微博打开对应的超话页面，右上角分享，选择复制链接 获得

       ```
       https://weibo.com/p/100808445c68f22a32b95efe6c3d025512d13c_-_feed
       ```

       其中

       ```
       100808445c68f22a32b95efe6c3d025512d13c
       ```

       就是我们需要的pid，将获取到的pid输入到application.yml中的wb.pid中，如需签到多个超话可以用上面的 方法获取pid后将多个pid永英文逗号分隔保存

3. ## 启动项目即可
