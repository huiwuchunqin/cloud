//导出
function exportMedia(){
	var url="/manage/res/exportMedia.html";
		downloadHelper(urlWithParam(url,getMediaParam()));
}
//导出
function exportMediaCheck(checkStatus,areaAdmin){
	var url="/manage/res/exportMediaCheck.html";
		downloadHelper(urlWithParam(url,getMediaCheckParam()));
}
//文档导出
function exportDoc(){
	var url="/manage/res/exportDoc.html";
	downloadHelper(urlWithParam(url,getDocParam()));
}
//文档审核导出
function exportDocCheck(){
	var url="/manage/res/exportDocCheck.html";
	downloadHelper(urlWithParam(url,getDocCheckParam()));
}

//练习导出
function exportExercise(){
	var url="/manage/exercise/exportExercise.html";
		downloadHelper(urlWithParam(url,getExerciseParam()))
}
//练习审核导出
function exportExerciseCheck(checkStatus,areaAdmin){
	var url="/manage/exercise/exportExerciseCheck.html";
	downloadHelper(urlWithParam(url,getExerciseCheckParam()));
}
//题目导出
function exportQuestion(){
	var url="/manage/question/exportQuestion.html";
		downloadHelper(urlWithParam(url,getQuestionParam()));
}
//题目审核导出
function exportQuestionCheck(checkStatus,areaAdmin){
	var url="/manage/question/exportQuestionCheck.html";
	downloadHelper(urlWithParam(url,getQuestionCheckParam()));
}
//导出音频
function exportAudio(){
	var url="/manage/audio/exportAudio.html";
	downloadHelper(urlWithParam(url,getAudioParam()));
}
//导出音频审核
function exportAudioCheck(){
	var url="/manage/audio/exportAudioCheck.html";
	downloadHelper(urlWithParam(url,getAudioCheckParam()));
}
//导出flash
function exportFlash(){
	var url="/manage/flash/exportFlash.html";
		downloadHelper(urlWithParam(url,getFlashParam()));
}
//导出
function exportFlashCheck(checkStatus,areaAdmin){
	var url="/manage/flash/exportFlashCheck.html";
		downloadHelper(urlWithParam(url,getFlashCheckParam()));
}