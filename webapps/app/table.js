function renderTable(obj) {
	if (obj.listItems) {
		var counter = 0;
		for (var i = 0; i < obj.listItems.length; i++) {
			counter++;
			if (i != obj.listItems.length - 1) {
				var counterItem = jQuery("<div name = 'counter' class='DivEmployeeCount'>"
						+ counter + "</div>");
				var nameItem = jQuery("<div name = 'employeeName' class='DivEmployeeName'>"
						+ obj.listItems[i].employeeName + "</div>");
				var departmentItem = jQuery("<div name = 'department' class='DivDepartment'>"
						+ obj.listItems[i].departmentName + "</div>");

				var row = jQuery("<div name = 'row'></div>");

				row.append(counterItem);
				row.append(nameItem);
				row.append(departmentItem);

				var left = jQuery("#content_left");

				left.append(row);

			} else {
				var counterItem = jQuery("<div name = 'counter' class='DivEmployeeCount' style='border-bottom-style:solid;'>"
						+ counter + "</div>");
				var nameItem = jQuery("<div name = 'employeeName' class='DivEmployeeName' style='border-bottom-style:solid;'>"
						+ obj.listItems[i].employeeName + "</div>");
				var departmentItem = jQuery("<div name = 'department' class='DivDepartment' style='border-bottom-style:solid;'>"
						+ obj.listItems[i].departmentName + "</div>");

				var row = jQuery("<div name = 'row'></div>");

				row.append(counterItem);
				row.append(nameItem);
				row.append(departmentItem);

				var left = jQuery("#content_left");

				left.append(row);
			}

			var trItem = jQuery("<tr class = 'trContent'></tr>");
			for (var j = 0; j < 31; j++) {

				var tdItem = jQuery("<td></td>");
				if (obj.listItems[i].attendenceList[j]) {

					var tdObj = obj.listItems[i].attendenceList[j];
					var n = j + 1;
					var isRender = false;

					if (n < 10) {
						var p = tdObj.date.substring(9, 10);
						if (p == n) {
							isRender = true;
						}
						var size = 11;
					}
					if (n >= 10) {
						var p = tdObj.date.substring(8, 10);
						if (p == n) {
							isRender = true;
						}
						var size = 11;
					}
					if (isRender) {
						if (tdObj.isHoliday == true) {
							if (tdObj.inHour || tdObj.outHour) {
								var inhour = jQuery("<div class='tdTop' style = 'color:red'></div>");
								var outhour= jQuery("<div class='tdBottom' style = 'color:red'></div>");
								if (tdObj.inHour) {
									inhour = jQuery("<div class='tdTop' style = 'background-color:yellow'>"
											+ tdObj.inHour.substring(size)
											+ "</div>");
								} else {
									inhour = jQuery("<div class='tdTop' style = 'background-color:yellow'></div>");
								}
								tdItem.append(inhour);

								if (tdObj.outHour) {
									outhour = jQuery("<div class='tdBottom' style = 'background-color:yellow'>"
											+ tdObj.outHour.substring(size)
											+ "</div>");
								} else {
									outhour = jQuery("<div class='tdBottom' style = 'background-color:yellow'></div>");
								}
								tdItem.append(outhour);
							} else {
								tdItem = jQuery("<td>休</td>");
							}
						} else {
							var inhour = jQuery("<div class='tdTop' style = 'color:red'></div>");
							var outhour= jQuery("<div class='tdBottom' style = 'color:red'></div>");
							
							if (tdObj.isMSpecial) {
								if (tdObj.mSpecialType == "1") {
									inhour = jQuery("<div class='tdTop' style = 'color:red'>漏考勤</div>");
								}
								if (tdObj.mSpecialType == "2") {
									if (tdObj.inHour) {
										inhour = jQuery("<div class='tdTop' style = 'color:red'>"
												+ tdObj.inHour.substring(size)
												+ "</div>");
									} else {
										inhour = jQuery("<div class='tdTop' style = 'color:red'></div>");
									}
								}
								if (tdObj.mSpecialType == "4") {
									inhour = jQuery("<div class='tdTop' style = 'background-color:orange'>年假</div>");
								}
								if (tdObj.mSpecialType == "5") {
									inhour = jQuery("<div class='tdTop' style = 'background-color:orange'>事假</div>");
								}
								if (tdObj.mSpecialType == "6") {
									inhour = jQuery("<div class='tdTop' style = 'background-color:orange'>病假</div>");
								}
								if (tdObj.mSpecialType == "7") {
									inhour = jQuery("<div class='tdTop' style = 'color:red'>旷工</div>");
								}
							} else {
								if (tdObj.inHour) {
									inhour = jQuery("<div class='tdTop'>"
											+ tdObj.inHour.substring(size)
											+ "</div>");
								} else {
									inhour = jQuery("<div class='tdTop'></div>");
								}
							}

							tdItem.append(inhour);

							if (tdObj.isASpecial) {
								if (tdObj.aSpecialType == "1") {
									outhour = jQuery("<div class='tdBottom' style = 'color:red'>漏考勤</div>");
								}
								if (tdObj.aSpecialType == "3") {
									if (tdObj.outHour) {
										outhour = jQuery("<div class='tdBottom' style = 'color:red'>"
												+ tdObj.outHour.substring(size)
												+ "</div>");
									} else {
										outhour = jQuery("<div class='tdBottom' style = 'color:red'></div>");
									}
								}
								if (tdObj.aSpecialType == "4") {
									outhour = jQuery("<div class='tdBottom' style = 'background-color:orange'>年假</div>");
								}
								if (tdObj.aSpecialType == "5") {
									outhour = jQuery("<div class='tdBottom' style = 'background-color:orange'>年假</div>");
								}
								if (tdObj.aSpecialType == "6") {
									outhour = jQuery("<div class='tdBottom' style = 'background-color:orange'>年假</div>");
								}
								if (tdObj.aSpecialType == "7") {
									outhour = jQuery("<div class='tdBottom' style = 'color:red'>旷工</div>");
								}
							} else {
								if (tdObj.outHour) {
									outhour = jQuery("<div class='tdBottom'>"
											+ tdObj.outHour.substring(size)
											+ "</div>");
								} else {
									outhour = jQuery("<div class='tdBottom' style = 'color:red'></div>");
								}
							}
							tdItem.append(outhour);

						}

					}

				}
				trItem.append(tdItem);
			}

			trItem.append(jQuery("<td>" + obj.listItems[i].numberOfSpecial
					+ "</td>"));
			trItem.append(jQuery("<td>" + obj.listItems[i].numberOfALeave
					+ "</td>"));
			trItem.append(jQuery("<td>" + obj.listItems[i].numberOfIllegalLeave
					+ "</td>"));
			trItem.append(jQuery("<td>" + obj.listItems[i].numberOfEventLeave
					+ "</td>"));
			trItem.append(jQuery("<td>" + obj.listItems[i].numberOfSickLeave
					+ "</td>"));
			trItem.append(jQuery("<td>" + obj.listItems[i].numberOfDayOnline
					+ "</td>"));
			if (obj.listItems[i].isFullAttendence) {
				trItem.append(jQuery("<td>1</td>"));
			} else {
				trItem.append(jQuery("<td>0</td>"));
			}
			if (obj.listItems[i].isHardWorking) {
				trItem.append(jQuery("<td>1</td>"));
			} else {
				trItem.append(jQuery("<td>0</td>"));
			}

			trItem.append(jQuery("<td>" + obj.listItems[i].remark + "</td>"));
			trItem
					.append(jQuery("<td>" + obj.listItems[i].punishment
							+ "</td>"));

			jQuery("#tableContent").append(trItem);

		}
	}

}