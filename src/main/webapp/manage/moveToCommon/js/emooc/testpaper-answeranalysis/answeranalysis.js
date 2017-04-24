/**
 * 用于解析试题答案,根据传入的不同试题类型，以及其json格式的答案，返回一个html文本数据
 * 
 * @param questionType问题类型
 * @param answers
 *            答案数组
 * 
 * 题型内容： 判断题：[{"answer":true}]
 * 选择题：[{"content":"选项1","name":"A","isanswer":true,"analyze":"分析内容"},{"content":"选项2","name":"B","analyze":"分析内容"},{"content":"选项3","name":"C","analyze":"分析内容"},{"content":"选项4","name":"D","analyze":"分析内容"}]
 * 多选题：[{"content":"选项1","name":"A","isanswer":true},{"content":"选项2","name":"B"},{"content":"选项3","name":"C","isanswer":true},{"content":"选项4","name":"D"},{"content":"选项5","name":"E"}]
 * 填空题：[{"answer":"答案1"},{"answer":"答案2"},{"answer":"答案n"}]
 * 简答题：[{"answer":"参考答案内容"}]
 * 完型填空：{"index":[{"content":"选项1","name":"A","isanswer":true,"analyze":"分析内容"},{"content":"选项2","name":"B","analyze":"分析内容"},{"content":"选项3","name":"C","analyze":"分析内容"},{"content":"选项4","name":"D","analyze":"分析内容"}]}
 * 阅读理解：{"testContent":"测试内容","index":[{"content":"选项1","name":"A","isanswer":true,"analyze":"分析内容"},{"content":"选项2","name":"B","analyze":"分析内容"},{"content":"选项3","name":"C","analyze":"分析内容"},{"content":"选项4","name":"D","analyze":"分析内容"}]}
 * 客观性填空题：[[{"answer":"答案1"}],[{"answer":"答案2"}],[{"answer":"答案n"}]]
 * 任务型阅读：[{"testContent":"测试内容","answer“:"答案"}]
 * 
 * <p>
 * <span>A.</span><span>dsf</span>&nbsp<span>A.</span><span>dsf</span><span>A.</span><span>dsf</span><span>A.</span><span>dsf</span>
 * </p>
 */
function parseAnswer(questionType, answers) {
	var answerHtml = "<p>";
	var name, analyze, content, answer;

	if (questionType == '10') {// 选择题

		_.each(answers, function(ans) {

			name = ans.name;
			answerHtml = answerHtml + "<span>" + name + ".</span>";
			content = ans.content;
			answerHtml = answerHtml + "<span>" + content + "</span>&nbsp";
		});
		return answerHtml + "</p>";
	} else if (questionType == '20') {// 多选题
		_.each(answers, function(ans) {

			name = ans.name;
			answerHtml = answerHtml + "<span>" + name + ".</span>";
			content = ans.content;
			answerHtml = answerHtml + "<span>" + content + "</span>&nbsp";
		});
		return answerHtml + "</p>";
	} else if (questionType == '30') {// 判断题
		_.each(answers, function(ans) {

			answer = ans.answer;
			if (answer) {
				answerHtml = answerHtml + "<span>对</span>";
			} else {
				answerHtml = answerHtml + "<span>错</span>"
			}
		});
		return answerHtml + "</p>";
	} else if (questionType == '40') {// 主观填空题
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '42') {// 单词听写
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '43') {// 单词默写
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '45') {// 客观性填空题
		_.each(answers, function(ans) {
			_.each(ans, function(a) {
				answer = a.answer;
				answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";
			});

		});
		return answerHtml + "</p>";
	} else if (questionType == '49') {// 完型填空

		_.each(answers.index, function(ans) {
			name = ans.name;
			answerHtml = answerHtml + "<span>" + name + ".</span>";
			content = ans.content;
			answerHtml = answerHtml + "<span>" + content + "</span>&nbsp";
		});
		return answerHtml + "</p>";

	} else if (questionType == '50') {// 简答题
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '60') {// 名词解释
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '70') {// 论述题
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '80') {// 计算题
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '90') {// 综合
		_.each(answers, function(ans) {

			answer = ans.answer;
			answerHtml = answerHtml + "<span>" + answer + "</span>&nbsp&nbsp";

		});
		return answerHtml + "</p>";
	} else if (questionType == '95') {// 阅读理解
		_.each(answers.index, function(ans) {
			name = ans.name;
			answerHtml = answerHtml + "<span>" + name + ".</span>";
			content = ans.content;
			answerHtml = answerHtml + "<span>" + content + "</span>&nbsp";
		});
		return answerHtml + "</p>";
	} else if (questionType == '99') {// 组合
		_.each(answers, function(ans) {
			_.each(ans.index, function(a) {
				name = a.name;
				answerHtml = answerHtml + "<span>" + name + ".</span>";
				content = a.content;
				answerHtml = answerHtml + "<span>" + content + "</span>&nbsp";
			});
		});
		return answerHtml + "</p>";
	} else if (questionType == '') {// 任务型阅读 暂无
		return null;
	}

}