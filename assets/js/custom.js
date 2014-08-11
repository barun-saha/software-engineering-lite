// Case study problem hide/show
$(document).on("click", "#problem-hide", function(){
	var curValue = $(this).text();
	var target = $("#case-study-problem");

	if (curValue == "Hide") {
		target.hide();
		$(this).text("Show");
	} else {
		target.show();
		$(this).text("Hide");
	}
});


function clearMark() {
	$("p.option img").attr("src", "transparent_2x2.png");
}

$(document).on("click", "button#btnSubmit", function(){
	clearMark();

	var nQuestions = $("div[id^=choices]").length;
	var correct = new Array(nQuestions);
	var answers = new Array(nQuestions);
	var nCorrect = 0;

	for (var i = 1; i <= nQuestions; i++) {
		correct[i] = $("input:hidden[id=qa_" + i + "]").val();

		var value = $("input[name=q_" + i + "]:checked").val();

		if ($("input:radio[name=q_" + i + "]:checked").val() > 0) {
			answers[i] = value;
		}
		else {
			answers[i] = -1;
		}

		if (answers[i] != correct[i]) {
			$("div#question_" + i + " p#option" + i + "_" + answers[i] + " img").attr("src", "exclamation.gif");
			$("div#question_" + i + " p#option" + i + "_" + answers[i] + " img").attr("height", "16");
			$("div#question_" + i + " p#option" + i + "_" + answers[i] + " img").attr("width", "16");
		}
		else {
			$("div#question_" + i + " p#option" + i + "_" + answers[i] + " img").attr("src", "accept.png");
			$("div#question_" + i + " p#option" + i + "_" + answers[i] + " img").attr("height", "16");
			$("div#question_" + i + " p#option" + i + "_" + answers[i] + " img").attr("width", "16");
			++ nCorrect;
		}
		$("div#question_" + i + " p#option" + i + "_" + correct[i]).addClass("correct");
	}

	var mesg = "";
	if (nCorrect == nQuestions)
		mesg = "Congrats! All your answers are correct!"
	else if (nCorrect == 0)
		mesg = "All of your answers are, unfortunately, wrong! Please revise the topics."
	else
		mesg = "" + nCorrect + " answer(s) out of " + nQuestions + " questions are correct!"

	$("#results").text(mesg);
	$("#results").css({"font-size": "1.1em", "background-color": "#FFFF66", "padding": "2px"});

});


$(document).on("click", "button#btnClear", function(){
	clearMark();
});
