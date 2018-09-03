// 存放主要交互逻辑js代码
// javascript 模块化
var seckill = {
    // 所有请求的URL
    URL: {
        detail: function () {
            return '/seckill/' + seckill.data.seckillId + "/detail";
        },
        exposer: function () {
            return '/seckill/' + seckill.data.seckillId + '/exposer';
        },
        execution: function () {
            return '/seckill/' + seckill.data.seckillId + '/' + seckill.data.exposer.md5 + '/execute'
        }
    },
    // 页面所有data
    data: {
        seckillId: '',
        seckill: {},
        exposer: {}
    },
    methods: {
        // 初始化页面渲染方法
        init: function () {
            seckill.data.seckillId = getQueryString("seckillId");
            $.get(seckill.URL.detail(), {}, function (result) {
                if (result && result.code == 0) {
                    seckill.data.seckill = result.data;
                    seckill.methods.initPage();
                } else {
                    layer.msg(result.msg);
                }
            });
        },
        initPage: function () {
            var data = seckill.data.seckill;
            $("#seckillName").text(data.name);
            // 用户手机认证,计时交互
            // 在cookie查找手机号
            var killPhone = $.cookie('killPhone');
            if (!validatePhone(killPhone)) {
                //绑定手机号
                //控制输出
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true, // 显示弹出层
                    backdrop: 'static', // 禁止位置关闭
                    keyboard: false, //关闭键盘事件
                });
                $("#killPhoneBtn").click(function (){
                    var inputPhone = $("#killPhoneKey").val()
                    if (validatePhone(inputPhone)) {
                        //电话写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7 , path: '/'});
                        // 刷新页面
                        window.location.reload();
                    }else {
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }
            //已经登陆
            seckill.methods.seckillUrl();
        },
        /* 查询秒杀地址 */
        seckillUrl: function () {
            // TODO 接口时间差计算
            $.post(seckill.URL.exposer(),{},function (result) {
                if (result && result.code == 0) {
                    seckill.data.exposer = result.data;
                    seckill.methods.countDown();
                }else {
                    layer.msg(result.msg);
                }
            });
        },
        // 时间判断,计时交互
        countDown: function () {
            var exposer = seckill.data.exposer;
            var seckillBox = $("#seckill-box");
            // 秒杀已经开启
            if (exposer.exposed) {
                seckill.methods.handleSeckillkill(seckillBox);
            }else { // 秒杀未开启： 秒杀结束，秒杀未开启
                    // 秒杀结束
                if (exposer.now > exposer.end) {
                    seckillBox.html('<label class="label label-danger">秒杀结束</label>');
                }else {
                    // 秒杀未开启
                    seckillBox.countdown(new Date(exposer.start), function (event) {
                        var format = event.strftime("秒杀倒计时: %D天 %H时 %M分 %S秒");
                        seckillBox.html(format);
                        /* 时间完成后回调事件 */
                    }).on('finish.countdown',function () {
                        seckill.methods.handleSeckillkill(seckillBox);
                    });
                }
            }
        },
        handleSeckillkill: function (node) {
            // 处理秒杀逻辑
            // 获取秒杀地址,控制实现逻辑, 执行秒杀
            node.hide().html('<button class="btn btn-primary btn-1g" id="killBtn">开始秒杀</button>').show(10);
            if (!seckill.data.exposer.md5) {
                seckill.methods.seckillUrl();
                return;
            }
            $("#killBtn").one('click', function () {
                // 执行秒杀请求
                // 1.禁用按钮
                $(this).addClass("disabled");
                // 2.发送秒杀请求
                $.post(seckill.URL.execution(),{},function (result) {
                    if (result && result.code == 0) {
                        var state = result.data.state;
                        var stateInfo = result.data.stateInfo;
                        // 显示秒杀结果
                        node.html('<span class="label label-success">' + stateInfo + '</span>');
                    }else {
                        layer.msg(result.msg);
                    }
                })
            });
        }
    }
}
function validatePhone(phone) {
    if (phone && phone.length == 11 && !isNaN(phone)) {
        return true;
    }else {
        return false;
    }
}

/**
 * 获取url地址上的参数
 * @param name
 * @returns {*}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}