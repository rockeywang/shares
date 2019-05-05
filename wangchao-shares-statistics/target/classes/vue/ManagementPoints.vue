<template>
  <div class="boxcontainer">
    <div class='head'>
      <Icon type="logo-buffer" size='60' color='#1296DB'/>
      <div class='head-content'>
        <p>您名下门店中，当前最低积分值：</p>
        <div>
          <span class='pointsnum'>{{pointsNum}}</span>
          <span class='view-more color-bule' @click='seeMore'>查看更多</span>
        </div>
      </div>
    </div>
    <div class='header'>
      <h2>生成积分记录</h2>
    </div>
    <div class='content'>
      <!-- 查询时间 -->
      <div class='content-time'>
        <p class='margin-top10'>查询时间：</p>
        <Row>
            <Col span="12">
                <DatePicker type="date" placeholder="开始时间" :value='startDate' style="width: 200px" @on-change='startDateChange'></DatePicker>
            </Col>
            <Col span="12">
                <DatePicker type="date" placeholder="结束时间" :value='endDate' style="width: 200px" @on-change='endDateChange'></DatePicker>
            </Col>
        </Row>
      </div>
      <!-- 选择门店 -->
      <div>
        <p><i class='color-red'>* </i>选择门店：</p>
        <div class='content-select'>
          <!-- 选择门店选择框 -->
           <div class='Tuhuselect' id='shopSelect'>
             <div class='Tuhuselect-head' @click='handleClose'>
              <span v-show='placehold'>请选择</span>
              <span v-text='selectMain'></span>
              <div>
                  <Icon type="ios-arrow-down" v-show='close'/>
                  <Icon type="ios-arrow-up" v-show='!close'/>
              </div>
            </div>
            <div v-show='optionShow' class='Tuhuselect-main'>
              <div class='iInput'><Input placeholder="请输入关键字"  id='shopInput' v-model='inputvalue' @on-change='inputChange'/></div>
              <!-- <Input /> -->
               <div class='Tuhuselect-option' >
                <ul>
                  <li
                  v-for="(item, index) in shops"
                  :value="item.shopId"
                  :key="index"
                  :id='index'
                  :class="selectCurrentIndex == index ? 'liVisited': ''"
                  @click='optionClick'
                  >
                  {{ item.shopName }}
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 选择状态 -->
      <div class='content-status'>
        <p>选择状态：</p>
        <Select v-model='statusItem' style="width:100px" @on-change='selectChange'>
            <Option v-for="item in status" :value="item.status" :key="item.value" >{{ item.label }}</Option>
        </Select>
      </div>
      <!-- 按钮 -->
      <div class='content-button'>
        <Button type='success' @click='search'>查询</Button>
      </div>
    </div>
    <!-- 标签页 -->
    <div class='main-tabs'>
      <Tabs type="card" @on-click='tabsClick' :animated='false'>
        <TabPane name='管理积分' label="管理积分">
          <!-- table -->
          <div class='main-table'>
            <Table  border :columns="columnsPoints" :data="pointsData"></Table>
              <div class="pagenum">
                <Page :current='currentpage' :total="dataCount" :page-size="pageSize"  class="paging" @on-change="changepage"></Page>
                <p>每页{{pageSize}}条数据，总计<span class='color-red'>{{dataCount}}</span>条</p>
              </div>
          </div>
        </TabPane>
        <TabPane name='历史记录' label="历史记录">
          <!-- table -->
          <div class='main-table'>
            <Table  border :columns="columnsPoints" :data="historyData"></Table>
              <div class="pagenum">
                <Page :current='historyCurrentpage' :total="historyDataCount" :page-size="pageSize"  class="paging" @on-change="historyChangepage"></Page>
                <p>每页{{pageSize}}条数据，总计<span class='color-red'>{{historyDataCount}}</span>条</p>
              </div>
          </div>
        </TabPane>
      </Tabs>
      <!-- 加载中 -->
    <Spin fix v-show="loadingShow" size='large'>
        <Icon type="ios-loading" size=18 style="animation: ani-demo-spin 1s linear infinite;"></Icon>
        <div>正在努力为您搜索，请稍后...</div>
    </Spin>
    </div>
    <!-- 评分详情-客户评论及个案 -->
    <Modal v-model='modal1' width="700" :styles="{top: '80px'}" v-show='modalShow'>
        <p slot="header">评分详情</p>
        <div class='modalTable'>
            <p class='font-weight700 marbottom_10'>基本信息：</p>
            <table border="1px" cellspacing="0">
              <tr><th>编号</th><td>{{pointsDetails1.workOrderId}}</td></tr>
              <tr><th>评分类型</th><td>{{pointsDetails1.integralType}}</td></tr>
              <tr v-show='pointsDetails1.orderNo'><th>订单号</th><td>{{pointsDetails1.orderNo}}</td></tr>
            </table>
            <p class='mar_10 font-weight700'>判定结果：</p>
            <table border="1px" cellspacing="0">
              <tr><th>项目类型</th><th>具体原因</th></tr>
              <tr><th>{{pointsDetails1.projectType}}</th><td>{{pointsDetails1.reason}}</td></tr>
            </table>
            <!-- 弹框加载中 -->
           <Spin fix v-show="loadingModal"> </Spin>
        </div>
        <div slot="footer" class='align-center modalButton'>
            <Button type='primary' size='large' @click="del">关闭</Button>
        </div>
    </Modal>

    <!-- 评分详情-盘库类 -->
    <Modal v-model='modal2' width="700" :styles="{top: '20px'}" v-show='modalShow'>
      <!-- 评分详情 -->
        <p slot="header">评分详情</p>
        <div class='modalTable'>
            <p class='font-weight700 marbottom_10'>基本信息：</p>
            <table border="1px" cellspacing="0">
              <tr><th>编号</th><td>{{pointsDetails2.workOrderId}}</td></tr>
              <tr><th>评分类型</th><td>{{pointsDetails2.integralType}}</td></tr>
            </table>
            <!-- 初次盘库明细： -->
             <div v-show='pointsDetails2.pankuList'>
              <p class='mar_10 font-weight700'>初次盘库明细：</p>
              <div v-for = '(item, index) in pointsDetails2.pankuList' :key='index'  class='marbottom_15'>
                  <table border="1px" cellspacing="0">
                      <tr><th>库位编号</th><td>{{item.pankuNo}}</td></tr>
                      <tr><th>理论商品数量</th><td>{{item.totalCount}}</td></tr>
                      <tr><th>实际商品数据</th><td>{{item.actualCount}}</td></tr>
                  </table>
              </div>
            </div>

            <!-- 初次盘库汇总： -->
            <p class='mar_10 font-weight700'>初次盘库汇总：</p>
            <table border="1px" cellspacing="0">
              <tr><th>商品差异量</th><td>{{pointsDetails2.diffCount}}</td></tr>
              <tr :class="pointsDetails2.diffRate>10?'bag_red':''"><th>商品差异率</th><td>{{pointsDetails2.diffRate}}</td></tr>
            </table>
             <!-- 终次盘库明细： -->
             <div v-show='pointsDetails2.finalPankuList'>
               <p class='mar_10 font-weight700'>终次盘库明细：</p>
              <div v-for = '(item, index) in pointsDetails2.finalPankuList' :key='index' class='marbottom_15'>
                <table border="1px" cellspacing="0">
                    <tr><th>库位编号</th><td>{{item.pankuNo}}</td></tr>
                    <tr><th>理论商品数量</th><td>{{item.totalCount}}</td></tr>
                    <tr><th>实际商品数据</th><td>{{item.actualCount}}</td></tr>
                </table>
              </div>
             </div>
            <!-- 终次盘库汇总： -->
            <div v-show='pointsDetails2.finalPankuList'>
              <p class='mar_10 font-weight700'>终次盘库汇总：</p>
              <table border="1px" cellspacing="0">
                <tr><th>商品差异量</th><td>{{pointsDetails2.finalDiffCount}}</td></tr>
                <tr :class="pointsDetails2.finalDiffRate>10?'bag_red':''"><th>商品差异率</th><td>{{pointsDetails2.finalDiffRate}}</td></tr>
              </table>
            </div>

            <!-- 弹框加载中 -->
           <Spin fix v-show="loadingModal"> </Spin>
        </div>
        <div slot="footer" class='align-center modalButton'>
            <Button type='primary' size='large' @click="del">关闭</Button>
        </div>
    </Modal>

     <!-- 评分详情-订单质检 日常质检评分 服务规范类 施工流程类-->
    <Modal v-model='modal3' width="700" :styles="{top: '50px'}" v-show='modalShow'>
        <p slot="header">评分详情</p>
        <div class='modalTable'>
            <p class='font-weight700 marbottom_10'>基本信息：</p>
            <table border="1px" cellspacing="0">
              <tr><th>编号</th><td>{{pointsDetails3.workOrderId}}</td></tr>
              <tr><th>评分类型</th><td>{{pointsDetails3.integralType}}</td></tr>
              <tr v-show='pointsDetails3.orderNo'><th>订单号</th><td>{{pointsDetails3.orderNo}}</td></tr>
              <tr v-show='pointsDetails3.stationNo'><th>工位号</th><td>{{pointsDetails3.stationNo}}</td></tr>
              <tr v-show='pointsDetails3.technician'><th>技师</th><td>{{pointsDetails3.technician}}</td></tr>
            </table>
            <div v-show='pointsDetails3.templateInfos'>
                <p class='mar_10 font-weight700'>{{pointsDetails3.title}}</p>
                <table border="1px" cellspacing="0">
                  <tr><th>监控时间</th><td>{{pointsDetails3.monitorTime}}</td></tr>
                </table>
                <div v-for='(item, index) in pointsDetails3.templateInfos' :key='index' class='fortable'>
                  <table border="1px" cellspacing="0">
                    <tr :class="item.score==0?'bag_red':''"><th>{{item.templateName}}</th><td>{{item.score}}</td></tr>
                    <tr><th>不合规项</th><td>{{item.reason}}</td></tr>
                    <tr><th>上传附件</th><td><span v-show='item.attachResp.value'><a :href="item.attachResp.value" target='_blank'>{{item.attachResp.name}}</a></span></td></tr>
                    <tr><th>备注</th><td>{{item.remark}}</td></tr>
                  </table>
                </div>
             </div>
            <!-- 弹框加载中 -->
           <Spin fix v-show="loadingModal"> </Spin>
        </div>
        <div slot="footer" class='align-center modalButton'>
            <Button type='primary' size='large' @click="del">关闭</Button>
        </div>
    </Modal>
    <!-- 管理积分变化通知 -->
    <Modal v-model='modal4' width="1200" :styles="{top: '80px'}"  v-show='modalShow'>
        <p slot="header">管理积分变化通知</p>
        <div class='modalContent'>
          <h2 class='color-red'>{{viewNotice.title}} [{{viewNotice.number}}]</h2>
          <div class='viewMain'>
            <!-- 头部 -->
            <div class='viewMain-head'>
              <p>发布日期：<span class='color-red'>{{viewNotice.date}}</span></p>
              <p>来源：<span>途虎养车</span></p>
            </div>
            <!-- 主要内容-质检单送达通知 -->
            <div class='viewMain-content' v-show='viewNotice.type === 1'>
              <p>尊敬的合作伙伴：</p>
              <div class='notice-main'>
                <p> 途虎养车工场店管理中心在{{viewNotice.createTime1.Year}}年{{viewNotice.createTime1.Month}}月{{viewNotice.createTime1.Day}}日的{{viewNotice.scoreType}}中发现贵门店{{viewNotice.shopName}}存在:</p>
                <p> {{viewNotice.scoreProject}} - {{viewNotice.scoreInstructions}}不合规</p>
                <p> 此行为违反了《途虎养车工场店管理办法》中的要求，并可能有损您及您的门店、贵店客户和工场店体系三方利益。如确认将会扣除{{viewNotice.shopName}}管理积分{{viewNotice.integral1}}分</p>
                <p> 如对以上情况有疑问，请在2日内向贵店途虎负责人提出，以免影响贵店管理积分。相关详情也可联系途虎门店客服。</p>
              </div>
              <div class='viewMain-content'>
                <p>请注意：</p>
                <p>如对于不合规情况的说明通过，则此项扣分取消。不会另行通知</p>
                <p> 如不合规情况核实，则核实后发送积分变换通知。</p>
                <p>关于积分变化及扣分详情，请关注S系统中管理积分记录。</p>
              </div>
              <!-- 底部 -->
              <div class='viewMain-footer'>
                <p>途虎工场店管理中心</p>
                <p>编号：{{viewNotice.number}}</p>
              </div>
            </div>
            <!-- 主要内容-积分变化通知 -->
            <div class='viewMain-content' v-show='viewNotice.type === 2'>
              <p>尊敬的合作伙伴：</p>
              <div class='notice-main'>
                <p> 途虎养车工场店管理中心在{{viewNotice.createTime1.Year}}年{{viewNotice.createTime1.Month}}月{{viewNotice.createTime1.Day}}日的{{viewNotice.scoreType}}中发现贵门店{{viewNotice.shopName}}存在:</p>
                <p> {{viewNotice.scoreProject}} - {{viewNotice.scoreInstructions}}不合规。<span v-show='viewNotice.orderNo'>相关订单号： </span>{{viewNotice.orderNo}}</p>
                <p>{{viewNotice.remark}}</p>
                <p> 此行为违反了《途虎养车工场店管理办法》中的要求，并可能有损您及您的门店、贵店客户和工场店体系三方利益。因此：</p>
                <p> 扣除{{viewNotice.shopName}}管理积分{{viewNotice.integral1}}分。</p>
                <p> 望贵店对此加以重视，避免类似情况再次发生。相关详情可以咨询贵店途虎负责人或联系途虎门店客服。</p>
              </div>
              <!-- 底部 -->
              <div class='viewMain-footer'>
                <p>途虎工场店管理中心</p>
                <p>编号：{{viewNotice.number}}</p>
              </div>
            </div>
            <!-- 主要内容-积分管理季度重置 -->
            <div class='viewMain-content' v-show='viewNotice.type === 3'>
              <p>尊敬的合作伙伴：</p>
              <div class='notice-main'>
                <p> 贵门店{{viewNotice.shopName}}在{{viewNotice.year}}年第{{viewNotice.season}}季度管理积分为{{viewNotice.fullScore}}。自{{viewNotice.createTime1.Year}}年{{viewNotice.createTime1.Month}}月{{viewNotice.createTime1.Day}}日起管理积分重置为{{viewNotice.integral1}}。</p>
              </div>
            </div>
            <div class='footer-line'> </div>
            <!-- 背景 -->
            <div class='bag'>
              <p>
              {{viewNotice.statusName}}
              </p>
            </div>
          </div>
            <!-- 弹框加载中 -->
           <Spin fix v-show="loadingModal"> </Spin>
        </div>
        <div slot="footer" class='align-center modalButton'>
            <Button type='primary' size='large' @click="del" v-show='viewNoticeshow'>关闭</Button>
            <Button type='primary' size='large' @click="ikown" v-show='!viewNoticeshow'>已知晓</Button>
        </div>
    </Modal>
    </div>
</template>

<script>
import {
  getLowestScore,
  investorFindShopInfo,
  investorScoreDetail,
  investorFindHistoryIntegralList,
  investorFindIntegraList,
  updateStatusById
} from '@/api/points'
export default {
  data () {
    return {
      user: '',
      // 用于改变状态的id
      id: '',
      // 历史记录列表是否请求
      isRequest: true,
      // 门店选择所选项的索引值
      selectCurrentIndex: -1,
      // 门店选择的选择框中‘请选择’显示否
      placehold: true,
      // 所选选项填入选择框中
      selectMain: '',
      selectShopId: '',
      // 选项显示否
      optionShow: false,
      // 选择框中箭头的显示
      close: true,
      // 输入框的值
      inputvalue: '',
      // 门店最低分值
      pointsNum: 0,
      // 状态选择框选择值
      statusItem: '',
      // 数据总数
      dataCount: 0,
      historyDataCount: 0,
      // 每页数据条数
      pageSize: 20,
      // 表头
      columnsPoints: [
        {
          title: '编号',
          key: 'number',
          align: 'center',
          maxWidth: 120,
          minWidth: 60
        },
        {
          title: '门店名',
          key: 'shopName',
          align: 'center',
          maxWidth: 180,
          minWidth: 90
        },
        {
          title: '评分类型',
          key: 'scoreType',
          align: 'center',
          maxWidth: 180,
          minWidth: 90,
          className: 'textareaClass'
        },
        {
          title: '评分项目',
          key: 'scoreProject',
          align: 'center',
          maxWidth: 150,
          minWidth: 80,
          className: 'textareaClass'
        },
        {
          title: '评分说明',
          key: 'scoreInstructions',
          align: 'center',
          minWidth: 150
        },
        {
          title: '分值',
          key: 'integral',
          align: 'center',
          maxWidth: 80,
          minWidth: 40
        },
        {
          title: '评分生成时间',
          key: 'createTime',
          align: 'center',
          maxWidth: 180,
          minWidth: 90
        },
        {
          title: '评分生效时间',
          key: 'effectiveTime',
          align: 'center',
          maxWidth: 150,
          minWidth: 80
        },
        {
          title: '评分详情',
          key: 'createBy',
          align: 'center',
          maxWidth: 150,
          minWidth: 80,
          render: (h, params) => {
            return h('div', [
              h('span', {
                style: {
                  color: '#0F8FCF',
                  cursor: 'pointer',
                  fontSize: '13px',
                  display: params.row.workOrderId ? 'block' : 'none'
                },
                on: {
                  click: () => {
                    this.viewDetails(params)
                  }
                }
              }, '查看详情'),
              h(
                'span',
                {
                  style: {
                    display: params.row.workOrderId ? 'none' : 'block',
                    'margin-top': '-10px'
                  }
                },
                '____'
              )
            ])
          }
        },
        {
          title: '状态',
          key: 'status',
          align: 'center',
          maxWidth: 100,
          minWidth: 50,
          render: (h, params) => {
            return h('div', {
              style: {
                background: params.row.status === 0 ? '#D9534F' : params.row.status === 1 ? '#337AB7' : params.row.status === 2 ? '#5CB85C' : params.row.status === 3 ? '#777777' : '',
                width: '50px',
                height: '20px',
                'line-height': '20px',
                'border-radius': '5px',
                'font-size': '11px',
                color: '#fff',
                margin: 'auto'
              }
            }, params.row.statusName)
          }
        }, {
          title: '操作',
          key: 'process',
          align: 'center',
          width: 100,
          render: (h, params) => {
            return h('div', [
              h(
                'Button',
                {
                  style: {
                    display: params.row.type ? 'block' : 'none'
                  },
                  on: {
                    click: () => {
                      this.preview(params)
                    }
                  }
                },
                '查看'
              )
            ])
          }
        }],
      // 管理积分的数据
      pointsData: [],
      // 历史记录的数据
      historyData: [],
      // 当前页
      currentpage: 1,
      historyCurrentpage: 1,
      // 开始日期
      startDate: '',
      // 结束日期
      endDate: '',
      // 选择门店
      shops: [],
      noshops: [{
        shopId: '-1',
        shopName: '暂无数据'
      }],
      // 选择状态
      status: [],
      // 管理积分状态
      status1: [
        {
          value: '全部',
          label: '全部',
          status: ''
        },
        {
          value: '澄清中',
          label: '澄清中',
          status: 0
        },
        {
          value: '已确认',
          label: '已确认',
          status: 1
        }
      ],
      // 历史记录状态
      status2: [
        {
          value: '全部',
          label: '全部',
          status: ''
        },
        {
          value: '已知晓',
          label: '已知晓',
          status: 2
        },
        {
          value: '已取消',
          label: '已取消',
          status: 3
        }
      ],
      loadingShow: false,
      loadingModal: false,
      // 客户评论及个案
      modal1: false,
      // 盘库类
      modal2: false,
      // 订单质检评分
      modal3: false,
      // 查看弹框
      modal4: false,
      // 所有的弹框显示否
      modalShow: false,
      // 评分类型-客户评论及个案
      pointsDetails1: '',
      // 评分类型-盘库类
      pointsDetails2: '',
      pointsDetails3: '',
      // 查看模板
      viewNotice: {
        createTime1: {
          Year: '',
          Month: '',
          Day: ''
        }
      },
      viewNoticeshow: true,
      currentTag: '管理积分'
    }
  },
  created () {
    let body = document.querySelector('body')
    body.addEventListener('click', (e) => {
      if (e.target.offsetParent) {
        if (e.target.offsetParent.id === 'shopSelect') {
          this.optionShow = !this.optionShow
        } else if (e.target.offsetParent.id === 'shopInput') {
          this.optionShow = true
        } else {
          this.optionShow = false
          this.close = true
        }
      } else {
        this.optionShow = false
        this.close = true
      }
    }, false)

    this.init()
  },

  methods: {
    init () {
      this.datePicker()
      // 用户
      this.user = this.$store.state.authority.user.account
      // 查询门店最低分值
      getLowestScore({
        userPhone: this.user
      }).then(data => {
        this.pointsNum = data.data
      }).catch(e => this.$swaggerApi.onError(e))
      this.status = this.status1
      // 选择门店的所有门店
      this.findShopInfo()
      this.findIntegraList()
    },
    // 确定开始结束日期
    datePicker () {
      // 确定查询时间的开始结束日期，默认当前时间的当前季度
      const today = new Date()
      // 显示年
      let y = today.getFullYear()
      // 显示月
      let m = today.getMonth() + 1
      if (m > 0 && m < 4) {
        this.startDate = y + '-01-01' + ' ' + '00:00:00'
        this.endDate = y + '-03-31' + ' ' + '23:59:59'
      } else if (m > 3 && m < 7) {
        this.startDate = y + '-04-01' + ' ' + '00:00:00'
        this.endDate = y + '-06-30' + ' ' + '23:59:59'
      } else if (m > 6 && m < 10) {
        this.startDate = y + '-07-01' + ' ' + '00:00:00'
        this.endDate = y + '-09-30' + ' ' + '23:59:59'
      } else {
        this.startDate = y + '-10-01' + ' ' + '00:00:00'
        this.endDate = y + '-12-31' + ' ' + '23:59:59'
      }
    },
    // 查看管理积分
    findIntegraList () {
      // 查看管理积分
      this.loadingShow = true
      investorFindIntegraList({
        startData: this.startDate,
        endData: this.endDate,
        pageIndex: this.currentpage,
        pageSize: this.pageSize,
        shopId: this.selectShopId,
        status: this.statusItem,
        userPhone: this.user
      }).then(({ data }) => {
        this.loadingShow = false
        this.dataCount = data.total
        if (data && data.list) {
          this.pointsData = this.changeShow(data.list)
        }
      }).catch((e) => {
        this.loadingShow = false
        this.$Message.error(e.message)
      })
    },
    // 查看历史记录
    findHistoryIntegralList () {
      this.loadingShow = true
      // 查看管理积分
      investorFindHistoryIntegralList({
        startData: this.startDate,
        endData: this.endDate,
        pageIndex: this.historyCurrentpage,
        pageSize: this.pageSize,
        shopId: this.selectShopId,
        status: this.statusItem,
        userPhone: this.user
      }).then(({ data }) => {
        this.loadingShow = false
        this.historyDataCount = data.total
        if (data && data.list) {
          this.historyData = this.changeShow(data.list)
        }
      }).catch((e) => {
        this.loadingShow = false
        this.$Message.error(e.message)
      })
    },
    // 选择门店
    findShopInfo () {
      investorFindShopInfo({
        shopName: this.selectMain,
        userPhone: this.user
      }).then(({ data }) => {
        if (data) {
          this.shops = data
        } else {
          this.shops = this.noshops
        }
      }).catch((e) => {
        this.$Message.error(e.message)
      })
    },
    // 状态
    changeShow (list) {
      if (list) {
        list.map(item => {
          item.statusName = item.status === 0 ? '澄清中' : item.status === 1 ? '已确认' : item.status === 2 ? '已知晓' : item.status === 3 ? '已取消' : ''
        })
      }
      return list
    },
    // 查看更多
    seeMore () {
      this.$router.push({ path: '/pointsViewMore' })
      // let routeData = this.$router.resolve({ path: '/pointsViewMore' })
      // window.open(routeData.href, 'viewMore')
    },
    // 开始日期发生改变时
    startDateChange (d) {
      this.startDate = d + ' ' + '00:00:00'
    },
    // 结束日期发生改变时
    endDateChange (d) {
      this.endDate = d + ' ' + '23:59:59'
    },
    // 管理积分页面转换
    changepage (index) {
      this.currentpage = index
      this.findIntegraList()
    },
    // 历史记录页面转换
    historyChangepage (index) {
      this.historyCurrentpage = index
      this.findHistoryIntegralList()
    },
    // 状态选择框
    selectChange () {
      // console.log(this.user)
      // console.log(this.statusItem)
    },
    // 管理积分/历史记录切换
    tabsClick (e) {
      this.currentTag = e
      this.statusItem = ''
      if (this.currentTag === '历史记录') {
        this.historyCurrentpage = 1
        this.findHistoryIntegralList()
        this.status = this.status2
      } else {
        this.currentpage = 1
        this.findIntegraList()
        this.status = this.status1
      }
    },
    // 点击门店选择的选择框时触发
    handleClose () {
      this.close = !this.close
    },
    // 门店选择的选项点击的时候触发
    optionClick (e) {
      // 请选择的不显示
      this.placehold = false
      // 显示选项
      this.optionShow = false
      // 选择框显示选中选项值
      this.selectMain = e.target.innerHTML
      this.selectShopId = e.target.value
      // 选中的选项添加样式
      this.selectCurrentIndex = e.target.id
      if (this.currentTag === '历史记录') {
        this.findHistoryIntegralList()
      } else {
        this.findIntegraList()
      }
    },
    // 门店选择输入框内容改变时触发
    inputChange () {
      // 请选择的不显示
      this.placehold = false
      this.selectMain = this.inputvalue
      this.findShopInfo()
    },
    // 查询
    search () {
      if (this.currentTag === '历史记录') {
        this.historyCurrentpage = 1
        this.findHistoryIntegralList()
      } else {
        this.currentpage = 1
        this.findIntegraList()
      }
    },
    // 查询评分详情
    viewPointsDetail (data) {
      if (data) {
        if (this.modal1) {
          this.pointsDetails1 = data
        } else if (this.modal2) {
          if (!data.finalPankuList.length) {
            data.finalPankuList = 0
          }
          this.pointsDetails2 = data
        } else if (this.modal3) {
          if (!data.templateInfos.length) {
            data.templateInfos = 0
          }
          this.pointsDetails3 = data
        }
      }
    },
    // 查看详情
    viewDetails (params) {
      this.loadingModal = true
      this.modalShow = true
      if (params.row.scoreType.indexOf('盘库类') > -1) {
        this.modal2 = true
      } else if (params.row.scoreType.indexOf('订单质检评分') > -1 || params.row.scoreType.indexOf('日常质检评分') > -1 || params.row.scoreType.indexOf('服务规范类') > -1 || params.row.scoreType.indexOf('施工流程类') > -1) {
        this.modal3 = true
      } else {
        this.modal1 = true
      }
      investorScoreDetail({
        workOrderId: params.row.workOrderId
      }).then(({ data }) => {
        this.loadingModal = false
        this.viewPointsDetail(data)
      }).catch((e) => {
        this.loadingModal = false
        this.$Message.error(e.message)
      })
    },
    // 关闭
    del () {
      this.modalShow = false
      // 显示关闭
      this.viewNoticeshow = true
      this.module()
    },
    // 已知晓
    ikown () {
      // 状态改变接口
      updateStatusById({
        id: this.id,
        userPhone: this.user
      }).then(({ data }) => {
        this.modalShow = false
        this.viewNoticeshow = true
        this.module()
        if (data) {
          this.findIntegraList()
        }
      }).catch((e) => {
        this.modalShow = false
        // 显示关闭
        this.viewNoticeshow = true
        this.module()
        this.$Message.error(e.message)
      })
    },
    // 查看
    preview (params) {
      this.id = params.row.id
      this.modalShow = true
      this.modal4 = true
      this.viewNotice = params.row
      // 弹框头部
      this.viewNotice.title = params.row.type === 1 ? '质检单送达通知' : params.row.type === 2 ? '积分变化通知' : params.row.type === 3 ? '管理积分季度重置' : ''
      // 日期
      if (!this.viewNotice.date) {
        this.viewNotice.date = params.row.createTime.split(' ')[0]
      }
      this.viewNotice.createTime1 = {
        Year: this.viewNotice.date.split('-')[0],
        Month: this.viewNotice.date.split('-')[1],
        Day: this.viewNotice.date.split('-')[2]
      }
      this.viewNotice.integral1 = Math.abs(params.row.integral)
      // 水印
      this.viewNotice.statusName = params.row.statusName
      if (params.row.status === 1) {
        // 显示已知晓
        this.viewNoticeshow = false
      } else {
        // 显示已知晓
        this.viewNoticeshow = true
      }
    },
    module () {
      this.modal1 = false
      this.modal2 = false
      this.modal3 = false
      this.modal4 = false
      this.modalShow = false
    }
  }
}
</script>

<style lang="less">
  .boxcontainer {
    background:#fff;
    padding:10px 20px;
    height:100%;
    .head{
      display:flex;
      width:100%;
      height:65px;
      margin:5px 0 20px;
      padding:3px 25px;
      background:#D9EDF7;
      .head-content{
        padding:5px 25px;
        p{
          font-size:11px;
        }
        .pointsnum{
          font-size:28px;
          font-weight:500;
        }
        .view-more{
          cursor: pointer;
        }
      }

    }
    .header{
      width:100%;
      padding:5px;
      border-bottom:1px solid #ccc;
    }
    .content{
      padding:10px 0;
      display:flex;
      &>div{
        padding-right:10px;
        display:flex;
        p{
          margin-top:7px;
          font-weight:700;
        }
      }
      .content-select{
        width:300px;
      }
    }
    .main-tabs{
      margin-top:10px;
      .ivu-spin-fix{
        background-color:transparent;
      }
    }
  }
.pagenum {
  display: flex;
  align-content: center;
  justify-content: center;
  p {
    font-size: 14px;
    line-height: 50px;
    margin-left: 5px;
  }
  .paging {
    margin-top: 10px;
  }
}
.modalButton{
  .ivu-btn-primary{
    width:100px;
  }
}
// 查看弹框样式
.modalContent{
  margin:0 auto;
  border:3px #EEEEEE solid;
  box-shadow:-2px -2px 0px #888888;
  border-radius:2px;
  padding:10px 80px;
  height:450px;
  overflow-y:scroll;
  h2{
    text-align:center;
  }
  .viewMain{
    margin:20px 0;
    border-top:2px dotted  #9B9B9B;
    position: relative;
    .bag{
      position:absolute;
      top:0;
      right:-30px;
      color:#E8CCCC;
      font-size:30px;
      transform:rotate(-30deg);
      border:1px solid #E8CCCC;
      border-radius:5px;
      p{
        margin:10px;
        padding:5px 10px;
        border:1px dashed #E8CCCC;
      }
    }
    .viewMain-head{
        display:flex;
        justify-content: center;
        align-items: center;
        margin-top:10px;
        font-size:14px;
        p{
          margin:0 20px;
        }
      }
      .viewMain-content{
        margin:20px 0;
        font-size:16px;
        font-weight:500;
        .notice-main{
          text-indent: 2em;
          line-height:30px;
        }
      }
      .viewMain-footer{
        text-align:right;
      }
  }
  .footer-line{
        width:100%;
        height:2px;
        border-top:2px dotted  #9B9B9B;
      }
}
.color-red{
  color:red;
}
.color-bule{
  color:#2997D2;
}
.font-weight700{
 font-weight:700;
}
.align-center{
  text-align:center;
}
.mar_10{
  margin:10px 0;
}
.pad_10{
  margin:0 10px;
}
.bag_red{
  background-color:red;
  color:#fff;
}
.marbottom_10{
  margin-bottom:10px;
}
.marbottom_15{
  margin-bottom:15px;
}
.modalTable{
  font-size:14px;
  max-height:500px;
  overflow-y:scroll;
  padding-right:5px;
  &::-webkit-scrollbar-track-piece {
    background-color: #f5f5f5;
  }
  &::-webkit-scrollbar {
    width: 3px;
    height: 2px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: #c2c2c2;
    background-clip: padding-box;
    min-height: 28px;
  }
  table,table tr th, table tr td { border:1px solid #bbb;word-break: break-all;white-space: pre-wrap; }
  table>tr th, table>tr td{width:50%;}
  table{border:none!important;}
  .fortable{
    tr th,tr td{
      border-top:0;
      width:50%;
      word-break: break-all;
      white-space: pre-wrap;
      padding:2px 5px;
    }
  }
  table{ width:100%; min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;}
}
</style>
<style lang='less' scoped>
.Tuhuselect{
  position:relative;
  .Tuhuselect-head{
    width:300px;
    // height:30px;
    border:1px solid #DCDEE2;
    border-radius:5px;
    line-height:10px;
    padding:10px;
    span:first-child{
      color:#ccc;
    }
    div{
      text-align:right;
      margin-top:-10px;
    }
  }
  .Tuhuselect-main{
    width:300px;
    margin-top:5px;
    z-index:999 !important;
    background:#fff;
    position:absolute;
    top:33px;
    left:0px;
    border:1px solid #DCDEE2;
    box-shadow:1px 2px 3px 1px #eee;
    border-radius:5px;
    .iInput{
      width:290px;
      height:25px;
      margin:5px 4px;
      padding:2px;
    }
    .Tuhuselect-option{
      max-height:150px;
      margin-top:10px;
      overflow-y: scroll;
      &::-webkit-scrollbar-track-piece {
        background-color: #f5f5f5;
      }
      &::-webkit-scrollbar {
        width: 3px;
        height: 2px;
      }
      &::-webkit-scrollbar-thumb {
        background-color: #c2c2c2;
        background-clip: padding-box;
        min-height: 28px;
      }
        li{
          list-style-type: none;
          padding:5px 10px;
        }
        li:hover{
          cursor: pointer;
          background-color:#F3F3F3;
        }
        li:visited{
          cursor: pointer;
          color:#F00;
        }
    }
    .liVisited{
      color:#288FEF;
      background-color:#F3F3F3;
    }
  }
}
</style>
