const
// 资源审核状态
_shareCheckStatus = [ {
	name : '不限',
	value : '',
	selected : true
}, {
	name : '未提交',
	value : '60'
}, {
	name : '待审核',
	value : '10'
}, {
	name : '已通过',
	value : '20'
}, {
	name : '已退回',
	value : '05'
} ], 

// 资源类型
_RESTYPE={
  'RES_TYPE_MEDIA':10,
  'RES_TYPE_SPECIAL_MEDIA':11,
  'RES_TYPE_Flash':15,
  'RES_TYPE_Audio':12,
  'RES_TYPE_DOC':20,
  'RES_TYPE_EXERCISE':30,
  'RES_TYPE_TESTPAPER':40,
  'RES_TYPE_QUESTION':50,
  'RES_TYPE_COURSE':60,
		
},

// 共享级别
_SHARELEVEL=[ {
	name : '不限',
	value : ' ',
	selected : true
}, {
	name : '个人私有',
	value : '10'
}, {
	name : '校内共享',
	value : '20'
}, {
	name : '区域共享',
	value : '30'
} ],

_RES_TYPE=[{
	name : '全部资源',
	value : ' ',
	selected : true
}, {
	name : '视频',
	value : '10'
}, {
	name : '文档',
	value : '20'
}, {
	name : '测验',
	value : '30'
}, {
	name : '题库',
	value : '50'
} ]

// 共享审核级别
_SHARECHECKLEVEL = [ {
	name : '不限',
	value : ' ',
	selected : true
},  {
	name : '校内共享',
	value : '20'
}, {
	name : '区域共享',
	value : '30'
} ],

// 创建模式
_LESSONMODE=[
{
	name:"不限",
	value:"",	
},
{
	name:"翻转课堂模式",
	value:"10",	
},{
	name:"自主创建模式",
	value:"0",
}],

//代理商级别
_AGENCY_LEVEL=[
	{
		name:"一级代理商",
		value:1,
	},
	{
		name:"二级代理商",
		value:2,
	},
	{
		name:"三级代理商",
		value:3,
	},
	{
		name:"四级代理商",
		value:4,
	},
	{
		name:"五级代理商",
		value:5,
	}
	]
,

//机构启用
_FLAG_COMPANY_VALIDAYE_YES=1,

//机构禁用
_FLAG_COMPANY_VALIDAYE_NO=0;