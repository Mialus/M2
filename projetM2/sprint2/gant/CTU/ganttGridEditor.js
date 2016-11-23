/*
 Copyright (c) 2012-2014 Open Lab
 Written by Roberto Bicchierai and Silvia Chelazzi http://roberto.open-lab.com
 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
function GridEditor(master) {
	this.master = master; // is the a GantEditor instance
	this.gridified = $.gridify($.JST.createFromTemplate({},
		"TASKSEDITHEAD"));
	this.element = this.gridified.find(
		".gdfTable").eq(1);
}

GridEditor.prototype.fillEmptyLines = function() {
		var factory = new TaskFactory();

		var rowsToAdd = 30 - this.element.find(
			".taskEditRow").size();

		//fill with empty lines
		for (var i = 0; i < rowsToAdd; i++) {
			var emptyRow = $.JST.createFromTemplate({},
				"TASKEMPTYROW");
			//click on empty row create a task and fill above
			var master = this.master;
			emptyRow.click(function(ev) {
				var emptyRow = $(this);
				//add on the first empty row only
				if (!master.canWrite || emptyRow.prevAll(
						".emptyRow").size() > 0)
					return

				master.beginTransaction();
				var lastTask;
				var start = new Date().getTime();
				var level = 0;
				if (master.tasks[0]) {
					start = master.tasks[0].start;
					level = master.tasks[0].level +1;
				}

				//fill all empty previouses
				emptyRow.prevAll(".emptyRow").andSelf()
					.each(function() {
						var ch = factory.build(
							-(new Date().getTime()  %32766),
							"", "", level, start, 1);
						var task = master.addTask(ch);
						lastTask = ch;
					});
				master.endTransaction();
				lastTask.rowElement.click();
				lastTask.rowElement.find(
						"[name=name]").focus() //focus to "name" input
					.blur(function() { //if name not inserted -> undo -> remove just added lines
						var imp = $(this);
						if (imp.val() == "") {
							lastTask.name = "Task " + (
								lastTask.getRow() + 1);
							imp.val(lastTask.name);
						}
					});
			});
			this.element.append(emptyRow);
		}
	};

GridEditor.prototype.addTask = function(task, row, hideIfParentCollapsed) {
    this.element.find("[taskId=" + task.id +"]").remove();

	var taskRow = $.JST.createFromTemplate(
		task, "TASKROW");
	//save row element on task
	task.rowElement = taskRow;

	this.bindRowEvents(task, taskRow);

	if (typeof(row) != "number") {
		var emptyRow = this.element.find(
			".emptyRow:first"); //tries to fill an empty row
		if (emptyRow.size() > 0)
			emptyRow.replaceWith(taskRow);
		else
			this.element.append(taskRow);
	} else {
		var tr = this.element.find(
			"tr.taskEditRow").eq(row);
		if (tr.size() > 0) {
			tr.before(taskRow);
		} else {
			this.element.append(taskRow);
		}

	}
	this.element.find(".taskRowIndex").each(
		function(i, el) {
			$(el).html(i + 1);
		});
	//[expand]
	if (hideIfParentCollapsed) {
		if (task.collapsed) taskRow.find(
			".exp-controller").removeClass(
			'exp');
		var collapsedDescendant = this.master
			.getCollapsedDescendant();
		if (collapsedDescendant.indexOf(task) >=
			0) taskRow.hide();
	}
	return taskRow;
};

GridEditor.prototype.refreshExpandStatus = function(task) {
		if (!task) return;
		//[expand]
		var child = task.getChildren();
		if (child.length > 0 && task.rowElement
			.has(".expcoll").length == 0) {
			task.rowElement.find(
				".exp-controller").addClass(
				'expcoll exp');
		} else if (child.length == 0 && task.rowElement
			.has(".expcoll").length > 0) {
			task.rowElement.find(
				".exp-controller").removeClass(
				'expcoll exp');
		}

		var par = task.getParent();
		if (par && par.rowElement.has(
				".expcoll").length == 0) {
			par.rowElement.find(
				".exp-controller").addClass(
				'expcoll exp');
		}

	}

GridEditor.prototype.refreshTaskRow = function(task) {
		var row = task.rowElement;
		var affDuration = parseInt(task.duration /
				(60 * 24)) + "j " + parseInt(task.duration /
				60) % 24 + "h " + task.duration %
			60 + "min";

		row.find(".taskRowIndex").html(task.getRow() +
			1);
		row.find(".indentCell").css(
			"padding-left", task.level * 16 +
			12);
		row.find("[name=name]").val(task.name);
		row.find("[name=code]").val(task.code);
		row.find("[status]").attr("status",task.status);
		if (task.start>0){
			row.find("[name=duration]").val(minsToString(task.duration));
			row.find("[name=start]").val(new Date(task.start).format('dd/MM/yy HH:mm')).updateOldValue(); // called on dates only because for other field is called on focus event
			row.find("[name=end]").val(new Date(task.end).format('dd/MM/yy HH:mm')).updateOldValue();
			row.find("[name=depends]").val(task.depends);
		}else{
			row.find("[name=start]").val(""); // called on dates only because for other field is called on focus event
			row.find("[name=end]").val("");
			row.find("[name=depends]").val("");
			row.find("[name=duration]").val("");
		}
		row.find(".taskAssigs").html(task.getAssigsString());

	};

GridEditor.prototype.redraw = function() {
//console.log(this.master.tasks.length);
	for (var i = 0; i < this.master.tasks.length; i++) {
//	console.log(this.master.tasks[i].name,this.master.tasks[i].start)
		if (this.master.tasks[i].start>0)
		this.refreshTaskRow(this.master.tasks[i]);
	}
};

GridEditor.prototype.reset = function() {
	this.element.find("[taskId]").remove();
};


GridEditor.prototype.bindRowEvents = function(task, taskRow) {
		var self = this;
		if (this.master.canWrite && task.canWrite) {
			self.bindRowInputEvents(task,
				taskRow);

		}/* else { //cannot write: disable input
			taskRow.find("input").attr(
				"readonly", true);
		}*/

		self.bindRowExpandEvents(task,
			taskRow);

		taskRow.find(".edit").click(function() {
			self.openFullEditor(task, taskRow)
		});
	};


GridEditor.prototype.bindRowExpandEvents = function(task, taskRow) {
		var self = this;
		//expand collapse
		taskRow.find(".exp-controller").click(
			function() {
				//expand?
				var el = $(this);
				var taskId = el.closest("[taskId]")
					.attr("taskId");
				var task = self.master.getTask(
					taskId);
				var descs = task.getDescendant();
				el.toggleClass('exp');
				task.collapsed = !el.is(".exp");
				var collapsedDescendant = self.master
					.getCollapsedDescendant();

				if (el.is(".exp")) {
					for (var i = 0; i < descs.length; i++) {
						var childTask = descs[i];
						if (collapsedDescendant.indexOf(
								childTask) >= 0) continue;
						childTask.rowElement.show();
					}

				} else {
					for (var i = 0; i < descs.length; i++)
						descs[i].rowElement.hide();
				}
				self.master.gantt.refreshGantt();

			});
	}

GridEditor.prototype.bindRowInputEvents = function(task, taskRow) {
		var self = this;

		//bind dateField on dates
		taskRow.find(".date").each(function() {
			var el = $(this);

			//start is readonly in case of deps
			if (task.depends && el.attr("name") ==
				"start") {
				el.attr("readonly", "true");
			} else {
				el.removeAttr("readonly");
			}

			el.click(function() {
				var inp = $(this);
				inp.datetimepicker({
					minTime:'8:00',
					maxTime:'20:30',
					format: 'd/m/y H:i',
					step: '30'
				});
			});

			el.blur(function(date) {
				var inp = $(this);
				if (inp.isValueChanged()) {
console.log("YOLO");

					if (!Date.isValid(inp.val(),
							'dd/MM/yy HH:mm')) {
						alert(GanttMaster.messages["INVALID_DATE_FORMAT"]);
						inp.val(inp.getOldValue());

					} else {
						var date = Date.parseString(inp.val(), 'dd/MM/yy HH:mm');
						var row = inp.closest("tr");
						var taskId = row.attr("taskId");
						var task = self.master.getTask(taskId);
						var lstart = task.start;
						var lend=date.getTime()+task.duration;
							//task.duration=0;
						
						console.log(inp.attr("name"));
						console.log("st",date.getTime());
console.log("end",lend);
console.log("dur",task.duration);
						if (inp.attr("name") =="start") {
						//console.log("inside",Date.parseString(inp.getOldValue()));
						if (task.start>0){
							var oldDate = Date.parseString(inp.getOldValue(),'dd/MM/yy HH:mm');
							lend += date.getTime() -oldDate.getTime();
							}else{
							var oldDate= new Date(lstart);
							
							}
							
							lstart = date.getTime();
							
							console.log("v1",task.name);
							console.log(task.start,"st",lstart);
							console.log(task.end,"end",lend);
							console.log("dur",task.duration);
							
							console.log(task.end,"end",lend);
							if (lstart >= lend) {
								var end_as_date = new Date(lstart);
								lend = end_as_date.add('d',task.duration).getTime();
							}

							//update task from editor
							lstart=dependMile(task,lstart);
							console.log("v2");
							console.log(task.start,"st",date.getTime());
							console.log(task.end,"end",lend);
							console.log("dur",task.duration);
							self.master.beginTransaction();
							self.master.moveTask(task,lstart);
							self.master.endTransaction();


						} else {
							lend = date.getTime();
							if (lstart >= lend) {
								lend = lstart;
							}
							console.log("v3");
							console.log(task.start,"st",date.getTime());
							console.log(task.end,"end",lend);
							console.log("dur",task.duration);
							//lend=lend+3600000*20; // this 20 hours are mandatory to reach the correct day end (snap to grid)
							lstart=dependMile(task,lstart);
							//update task from editor
							self.master.beginTransaction();
							self.master.changeTaskDates(task, lstart, lend);
							self.master.endTransaction();
						}

						inp.updateOldValue(); //in order to avoid multiple call if nothing changed
						inp.datetimepicker('destroy');
					}
				}
			});
		});


		//binding on blur for task update (date exluded as click on calendar blur and then focus, so will always return false, its called refreshing the task row)
		taskRow.find("input:not(.date)").focus(
			function() {
				$(this).updateOldValue();

			}).blur(function() {
			var el = $(this);
			if (el.isValueChanged()) {
				var row = el.closest("tr");
				var taskId = row.attr("taskId");

				var task = self.master.getTask(
					taskId);

				//update task from editor
				var field = el.attr("name");
				task.modif=true;

				self.master.beginTransaction();

				if (field == "depends") {
					var oldDeps = task.depends;
					task.depends = el.val();

					//start is readonly in case of deps
					if (task.depends) {
						row.find("[name=start]").attr(
							"readonly", "true");
					} else {
						row.find("[name=start]").removeAttr(
							"readonly");
					}


					// update links
					var linkOK = self.master.updateLinks(
						task);
					if (linkOK) {
						//synchronize status from superiors states
						var sups = task.getSuperiors();
						for (var i = 0; i < sups.length; i++) {
							if (!sups[i].from.synchronizeStatus())
								break;
						}
						self.master.changeTaskDates(
							task, task.start, task.end); // fake change to force date recomputation from dependencies
					}

				} else if (field == "duration") {


					//fetch data from duration cell format into mins
					var affDuration = row.find(
						"[name=duration]").val();
					dur = stringToMins(row.find(
						"[name=duration]").val());
					el.val(dur);
					var newEnd =
						computeEndByDuration(task.start,
							dur);
					self.master.changeTaskDates(task,
						task.start, newEnd);

				} else if (field == "name" && el.val() ==
					"") { // remove unfilled task
					task.deleteTask();

				} else {
					task[field] = el.val();
				}
				self.master.endTransaction();
			}
		});

		//cursor key movement
		taskRow.find("input").keydown(
			function(event) {
				var theCell = $(this);
				var theTd = theCell.parent();
				var theRow = theTd.parent();
				var col = theTd.prevAll("td").size();

				var ret = true;
				switch (event.keyCode) {

					case 37: //left arrow
						if (this.selectionEnd == 0)
							theTd.prev().find("input").focus();
						break;
					case 39: //right arrow
						if (this.selectionEnd == this.value
							.length)
							theTd.next().find("input").focus();
						break;

					case 38: //up arrow
						var prevRow = theRow.prev();
						var td = prevRow.find("td").eq(
							col);
						var inp = td.find("input");

						if (inp.size() > 0)
							inp.focus();
						break;
					case 40: //down arrow
						var nextRow = theRow.next();
						var td = nextRow.find("td").eq(
							col);
						var inp = td.find("input");
						if (inp.size() > 0)
							inp.focus();
						else
							nextRow.click(); //create a new row
						break;
					case 36: //home
						break;
					case 35: //end
						break;

					case 9: //tab
					case 13: //enter
						break;
				}
				return ret;

			}).focus(function() {
			$(this).closest("tr").click();
		});


		//change status
		taskRow.find(".taskStatus").click(
			function() {
				var el = $(this);
				var tr = el.closest("[taskId]");
				var taskId = tr.attr("taskId");
				var task = self.master.getTask(
					taskId);

				var changer = $.JST.createFromTemplate({},
					"CHANGE_STATUS");
				changer.find("[status=" + task.status +
					"]").addClass("selected");
				changer.find(".taskStatus").click(
					function(e) {
						e.stopPropagation();
						var newStatus = $(this).attr(
							"status");
						changer.remove();
						self.master.beginTransaction();
						task.changeStatus(newStatus);
						self.master.endTransaction();
						el.attr("status", task.status);
					});
				el.oneTime(3000, "hideChanger",
					function() {
						changer.remove();
					});
				el.after(changer);
			});



		//bind row selection
		taskRow.click(function() {
			var row = $(this);
			//var isSel = row.hasClass("rowSelected");
			row.closest("table").find(".rowSelected").removeClass("rowSelected");
			row.addClass("rowSelected");

			//set current task
			self.master.currentTask = self.master
				.getTask(row.attr("taskId"));

			//move highlighter
			self.master.gantt.synchHighlight();

			//if offscreen scroll to element
			var top = row.position().top;
			if (top > self.element.parent().height()) {
				row.offsetParent().scrollTop(top -
					self.element.parent().height() +
					100);
			} else if (top < 40) {
				row.offsetParent().scrollTop(row.offsetParent()
					.scrollTop() - 40 + top);
			}
		});

	};
	
GridEditor.prototype.openFullEditor = function(task, taskRow) {
		var self = this;
		task.modif=true;
		//task editor in popup
		var taskId = taskRow.attr("taskId");

		//make task editor
		var taskEditor = $.JST.createFromTemplate({},
			"TASK_EDITOR");

		taskEditor.find("#name").val(task.name);
		taskEditor.find("#description").val(
			task.description);
		taskEditor.find("#code").val(task.code);
		taskEditor.find("#progress").val(task
			.progress ? parseFloat(task.progress) :
			0);
		taskEditor.find("#status").attr(
			"status", task.status);


		if (task.startIsMilestone)
			taskEditor.find("#startIsMilestone")
			.attr("checked", true);
		if (task.endIsMilestone)
			taskEditor.find("#endIsMilestone").attr(
				"checked", true);
		taskEditor.find("#duration").val(
			minsToString(task.duration));

		var startDate = taskEditor.find(
			"#start");
		startDate.val(new Date(task.start).format(
			'dd/MM/yy HH:mm'));


		//start is readonly in case of deps
		if (task.depends) {
			startDate.attr("readonly", "true");
		} else {
			startDate.removeAttr("readonly");
		}

		taskEditor.find("#end").val(new Date(
			task.end).format('dd/MM/yy HH:mm'));

		var oldSt = Date.parseString(
			taskEditor.find("#start").val(),
			'dd/MM/yy HH:mm').getTime();

		//make assignments table
		var assigsTable = taskEditor.find(
			"#assigsTable");
		assigsTable.find("[assigId]").remove();
		// loop on already assigned resources
		for (var i = 0; i < task.assigs.length; i++) {
			var assig = task.assigs[i];
			var assigRow = $.JST.createFromTemplate({
				task: task,
				assig: assig
			}, "ASSIGNMENT_ROW");
			assigsTable.append(assigRow);
		}

		//define start end callbacks
		function startChangeCallback(date) {
			var dur = stringToMins(taskEditor.find(
				"#duration").val());
			taskEditor.find("#end").val(new Date(
				computeEndByDuration(date.getTime(),
					dur)).format('dd/MM/yy HH:mm'));
		}

		function endChangeCallback(end) {
			var start = Date.parseString(
				taskEditor.find("#start").val(),
				'dd/MM/yy HH:mm');

			if (end.getTime() < start.getTime()) {
				var dur = parseInt(taskEditor.find(
					"#duration").val());
				dur = stringToMins(taskEditor.find(
					"#duration").val());
				start = incrementDateByWorkingDays(
					end.getTime(), -dur);
				taskEditor.find("#start").val(new Date(
					computeStart(start)).format(
					'dd/MM/yy HH:mm'));
			} else {
				taskEditor.find("#duration").val(
					minsToString(recomputeDuration(
						start.getTime(), end.getTime()
					)));
			}
		}


		if (!self.master.canWrite || !task.canWrite) {
			taskEditor.find("input,textarea").attr(
				"readOnly", false);
			taskEditor.find(
				"input:checkbox,select").attr(
				"disabled", true);
		} else {

			taskEditor.find("#start").click(
				function() {
					if (task.depends && $(this).attr(
							"name") == "start") {
						$(this).attr("readonly", "true");
					} else {
						$(this).removeAttr("readonly");
					}
					var inp = $(this);
					inp.datetimepicker({


						onSelectDate: startChangeCallback,
						format: 'd/m/y H:i',
						step: '30'
					}).datetimepicker('show');
				}).blur(function() {
				var inp = $(this);

				if (!Date.isValid(inp.val(),
						'dd/MM/yy HH:mm')) {
					alert(GanttMaster.messages[
						"INVALID_DATE_FORMAT"]);
					inp.val(inp.getOldValue());
				} else {
					startChangeCallback(Date.parseString(
						inp.val(), 'dd/MM/yy HH:mm'))
				}
				inp.datetimepicker('destroy');
			});

			//bind dateField on dates
			taskEditor.find("#end").click(
				function() {
					$(this).datetimepicker({
						onSelectDate: endChangeCallback,
						format: 'd/m/y H:i',
						step: '30'
					}).datetimepicker('show');;
				}).blur(function() {
				var inp = $(this);
				if (!Date.isValid(inp.val(),
						'dd/MM/yy HH:mm')) {
					alert(GanttMaster.messages[
						"INVALID_DATE_FORMAT"]);
					inp.val(inp.getOldValue());
				} else {
					endChangeCallback(Date.parseString(
						inp.val(), 'dd/MM/yy HH:mm'))
				}
				inp.datetimepicker('destroy');
			});

			//bind blur on duration
			taskEditor.find("#duration").change(
				function() {
					var start = Date.parseString(
						taskEditor.find("#start").val(),
						'dd/MM/yy HH:mm');
					var el = $(this);
					var dur = stringToMins(el.val());
					taskEditor.find("#end").val(new Date(
						computeEndByDuration(start.getTime(),
							dur)).format('dd/MM/yy HH:mm'));
				});


			//bind add assignment
			taskEditor.find("#addAssig").click(
				function() {
					var assigsTable = taskEditor.find(
						"#assigsTable");
					var assigRow = $.JST.createFromTemplate({
						task: task,
						assig: {
						    id:-(new Date().getTime()  %32766) 
						}
					}, "ASSIGNMENT_ROW");
					assigsTable.append(assigRow);
				});

			taskEditor.find("#status").click(
				function() {
					var tskStatusChooser = $(this);
					var changer = $.JST.createFromTemplate({},
						"CHANGE_STATUS");
					changer.find("[status=" + task.status +
						"]").addClass("selected");
					changer.find(".taskStatus").click(
						function(e) {
							e.stopPropagation();
							tskStatusChooser.attr("status",
								$(this).attr("status"));
							changer.remove();
						});
					tskStatusChooser.oneTime(3000,
						"hideChanger",
						function() {
							changer.remove();
						});
					tskStatusChooser.after(changer);
				});


			//save task
			taskEditor.find("#saveButton").click(
				function() {
					var task = self.master.getTask(
						taskId); // get task again because in case of rollback old task is lost

					self.master.beginTransaction();
					task.name = taskEditor.find(
						"#name").val();
					task.description = taskEditor.find(
						"#description").val();
					task.code = taskEditor.find(
						"#code").val();
					task.progress = parseFloat(
						taskEditor.find("#progress").val()
					);
					task.duration = stringToMins(
						taskEditor.find("#duration").val()
					);
					task.startIsMilestone =
						taskEditor.find(
							"#startIsMilestone").is(
							":checked");
					task.endIsMilestone = taskEditor.find(
						"#endIsMilestone").is(
						":checked");
					//set assignments
					taskEditor.find("tr[assigId]").each(
						function() {
							var trAss = $(this);
							var assId = trAss.attr(
								"assigId");
							var resId = trAss.find(
								"[name=resourceId]").val();
							var roleId = trAss.find(
								"[name=roleId]").val();
							var effort = millisFromString(
								trAss.find("[name=effort]").val()
							);


							//check if an existing assig has been deleted and re-created with the same values
							var found = false;
							for (var i = 0; i < task.assigs
								.length; i++) {
								var ass = task.assigs[i];

								if (assId == ass.id) {
									ass.effort = effort;
									ass.roleId = roleId;
									ass.resourceId = resId;
									ass.touched = true;
									found = true;
									break;

								} else if (roleId == ass.roleId &&
									resId == ass.resourceId) {
									ass.effort = effort;
									ass.touched = true;
									found = true;
									break;

								}
							}

							if (!found) { //insert
								var ass = task.createAssignment(
									-(new Date().getTime()  %32766),
									resId, roleId, effort);
								ass.touched = true;
							}

						});

					//remove untouched assigs
					task.assigs = task.assigs.filter(
						function(ass) {
							var ret = ass.touched;
							delete ass.touched;
							return ret;
						});

					//change dates
					//task.setPeriod(Date.parseString(taskEditor.find("#start").val(),'dd/MM/yy HH:mm').getTime(),task.duration+Date.parseString(taskEditor.find("#start").val(),'dd/MM/yy HH:mm').getTime());	

					if (oldSt < ((new Date(Date.parseString(
							taskEditor.find("#end").val(),
							'dd/MM/yy HH:mm'))).getTime()) ||
						((new Date(Date.parseString(
								taskEditor.find("#start").val(),
								'dd/MM/yy HH:mm'))).getTime() <
							(new Date(Date.parseString(
								taskEditor.find("#end").val(),
								'dd/MM/yy HH:mm'))).getTime())
					) {
						self.master.moveTask(task, (new Date(
							Date.parseString(taskEditor.find(
									"#start").val(),
								'dd/MM/yy HH:mm'))).getTime());
					} else {
						alert(GanttMaster.messages[
							"TASK_ERROR_END"]);
					}

					//change status
					task.changeStatus(taskEditor.find(
						"#status").attr("status"));

					if (self.master.endTransaction()) {
						$("#__blackpopup__").trigger(
							"close");
					}
				});
		}

		var ndo = createBlackPage(800, 500).append(
			taskEditor);
	};