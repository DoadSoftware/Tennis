var match_data;
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;	
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
}
function afterPageLoad(whichPageHasLoaded)
{
	switch (whichPageHasLoaded) {
	case 'SETUP':
		$('#homeFirstPlayerId').select2();
		$('#homeSecondPlayerId').select2();
		$('#awayFirstPlayerId').select2();
		$('#awaySecondPlayerId').select2();
		break;
	}
}
function initialiseForm(whatToProcess, dataToProcess)
{
	switch (whatToProcess) {
	case 'SETUP':
		
		if(dataToProcess) {
			document.getElementById('matchFileName').value = dataToProcess.matchFileName;
			document.getElementById('tournament').value = dataToProcess.tournament;
			document.getElementById('matchIdent').value = dataToProcess.matchIdent;
			document.getElementById('matchType').value = dataToProcess.matchType;
			document.getElementById('homeFirstPlayerId').value = dataToProcess.homeFirstPlayerId;
			document.getElementById('homeSecondPlayerId').value = dataToProcess.homeSecondPlayerId;
			document.getElementById('awayFirstPlayerId').value = dataToProcess.awayFirstPlayerId;
			document.getElementById('awaySecondPlayerId').value = dataToProcess.awaySecondPlayerId;
		} else {
			document.getElementById('matchFileName').value = '';
			document.getElementById('tournament').value = '';
			document.getElementById('matchIdent').value = '';
			document.getElementById('matchType').selectedIndex = 0;
			document.getElementById('homeFirstPlayerId').selectedIndex = 0;
			document.getElementById('homeSecondPlayerId').selectedIndex = 0;
			document.getElementById('awayFirstPlayerId').selectedIndex = 0;
			document.getElementById('awaySecondPlayerId').selectedIndex = 0;
		}
		processUserSelection($('#matchType'));
		afterPageLoad('SETUP');
		break;
	}
}
function uploadFormDataToSessionObjects(whatToProcess)
{
	var formData = new FormData();
	var url_path;

	$('input, select, textarea').each(
		function(index){  
			if($(this).is("select")) {
				formData.append($(this).attr('id'),$('#' + $(this).attr('id') + ' option:selected').val());  
			} else {
				formData.append($(this).attr('id'),$(this).val());  
			}	
		}
	);
	
	switch(whatToProcess.toUpperCase()) {
	case 'RESET_MATCH':
		url_path = 'reset_and_upload_match_setup_data';
		break;
	case 'SAVE_MATCH':
		url_path = 'upload_match_setup_data';
		break;
	}
	
	$.ajax({    
		headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')},
        url : url_path,     
        data : formData,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',     
        success : function(data) {

        	switch(whatToProcess.toUpperCase()) {
        	case 'RESET_MATCH':
        		alert('Match has been reset');
        		processWaitingButtonSpinner('END_WAIT_TIMER');
        		break;
        	case 'SAVE_MATCH':
        		document.setup_form.method = 'post';
        		document.setup_form.action = 'back_to_match';
        	   	document.setup_form.submit();
        		break;
        	}
        	
        },    
        error : function(e) {    
       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
        }    
    });		
	
}
function processUserSelection(whichInput)
{	
	var error_msg = '';

	switch ($(whichInput).attr('name')) {
	case 'select_serving_player':
		if(match_data.sets.length <= 0) {
			alert('No set started yet. Serve selection is not available');
			return false;			
		}
		match_data.sets.forEach(function(set,set_index,set_arr){
			if(set.set_status.toLowerCase() == 'start') {
				set.games.forEach(function(game,game_index,game_arr){
					if(game.game_status.toLowerCase() == 'start') {
						error_msg = 'Game number ' + game.game_number + ' already in play. End this game first before starting a new one';
					}
				});
			}
		});
		if(error_msg) {
			processWaitingButtonSpinner('START_WAIT_TIMER');
			processTennisProcedures('LOG_SERVE',null);
		} else {
			alert('No game started yet. Serve selection is not available');
			return false;			
		}
		break;
	case 'matchType':
		if($('#matchType option:selected').val() == 'singles') {
			document.getElementById('select_double_player_row').style.display = 'none';
		} else {
			document.getElementById('select_double_player_row').style.display = '';
		}
		break;
	case 'start_set_btn': 
		match_data.sets.forEach(function(set,set_index,set_arr){
			if(set.set_status.toLowerCase() == 'start') {
				error_msg = 'Set number ' + set.set_number + ' already in play. End this set first before starting a new one';
			}
		});
		if(error_msg) {
			alert(error_msg);
			return false;			
		} else {
			processWaitingButtonSpinner('START_WAIT_TIMER');
			processTennisProcedures('LOG_SET','START');
		}
		break;
	case 'end_set_btn': case 'reset_set_btn':
		error_msg = 'Cannot find any started set. Please start a set first';
		match_data.sets.forEach(function(set,set_index,set_arr){
			if(set.set_status.toLowerCase() == 'start') {
				error_msg = '';
			}
		});
		if(error_msg) {
			alert(error_msg);
			return false;			
		} else {
			switch ($(whichInput).attr('name')) {
			case 'end_set_btn': 
				if(confirm('Confirm ' + $('#select_set_winner option:selected').text() + ' wins this set?')) {
					processWaitingButtonSpinner('START_WAIT_TIMER');
					processTennisProcedures('LOG_SET','END');
				}
				break;
			case 'reset_set_btn':
				if(confirm('Do you really wish to RESET this set?')) {
					processWaitingButtonSpinner('START_WAIT_TIMER');
					processTennisProcedures('LOG_SET','RESET');
				}
				break;
			}
		}
		break;
	case 'start_game_btn':
		if(match_data.sets.length <= 0) {
			alert('No set started yet. Starting a game before starting a set is an invalid selection');
			return false;			
		}
		match_data.sets.forEach(function(set,set_index,set_arr){
			if(set.set_status.toLowerCase() == 'start') {
				set.games.forEach(function(game,game_index,game_arr){
					if(game.game_status.toLowerCase() == 'start') {
						error_msg = 'Game number ' + game.game_number + ' already in play. End this game first before starting a new one';
					}
				});
			}
		});
		if(error_msg) {
			alert(error_msg);
			return false;			
		} else {
			processWaitingButtonSpinner('START_WAIT_TIMER');
			processTennisProcedures('LOG_GAME','START');
		}
		break;
	case 'end_game_btn': case 'reset_game_btn':
		if(match_data.sets.length <= 0) {
			alert('No set started yet. This button is not available');
			return false;			
		}
		error_msg = 'Cannot find any started game. Please start a game first before using this option';
		match_data.sets.forEach(function(set,set_index,set_arr){
			if(set.set_status.toLowerCase() == 'start') {
				set.games.forEach(function(game,game_index,game_arr){
					if(game.game_status.toLowerCase() == 'start') {
						error_msg = '';
					}
				});
			}
		});
		if(error_msg) {
			alert(error_msg);
			return false;			
		} else {
			switch ($(whichInput).attr('name')) {
			case 'end_game_btn': 
				if(confirm('Confirm ' + $('#select_game_winner option:selected').text() + ' wins this game?')) {
					processWaitingButtonSpinner('START_WAIT_TIMER');
					processTennisProcedures('LOG_GAME','END');
				}
				break;
			case 'reset_game_btn':
				if(confirm('Do you reallt wish to RESET this game?')) {
					processWaitingButtonSpinner('START_WAIT_TIMER');
					processTennisProcedures('LOG_GAME','RESET');
				}
				break;
			}
		}
		break;
	case 'load_scene_btn':
		/*if(checkEmpty($('#vizIPAddress'),'IP Address Blank') == false
			|| checkEmpty($('#vizPortNumber'),'Port Number Blank') == false) {
			return false;
		}*/
	  	document.initialise_form.submit();
		break;
	case 'cancel_match_setup_btn':
		document.setup_form.method = 'post';
		document.setup_form.action = 'back_to_match';
	   	document.setup_form.submit();
		break;
	case 'matchFileName':
		if(document.getElementById('matchFileName').value) {
			document.getElementById('matchFileName').value = 
				document.getElementById('matchFileName').value.replace('.xml','') + '.xml';
		}
		break;
	case 'save_match_btn': case 'reset_match_btn':
		switch ($(whichInput).attr('name')) {
		case 'reset_match_btn':
	    	if (confirm('The setup selections of this match will be retained ' +
	    			'but the match data will be deleted permanently. Are you sure, you want to RESET this match?') == false) {
	    		return false;
	    	}
			break;
		}
		if (!checkEmpty(document.getElementById('matchFileName'),'Match Name')) {
			return false;
		} 
		if($('#homeFirstPlayerId option:selected').val() == $('#awayFirstPlayerId option:selected').val()) {
			alert('Player dupication found. Please choose different players');
			return false;
		}
		if($('#matchType option:selected').val() == 'doubles') {
			if($('#homeSecondPlayerId option:selected').val() == $('#awaySecondPlayerId option:selected').val()
				|| $('#homeFirstPlayerId option:selected').val() == $('#awaySecondPlayerId option:selected').val()
				|| $('#homeSecondPlayerId option:selected').val() == $('#awayFirstPlayerId option:selected').val()) {
				alert('Player dupication found. Please choose different players');
				return false;
			}
		}	
		switch ($(whichInput).attr('name')) {
		case 'save_match_btn': 
			uploadFormDataToSessionObjects('SAVE_MATCH');
			break;
		case 'reset_match_btn':
			processWaitingButtonSpinner('START_WAIT_TIMER');
			uploadFormDataToSessionObjects('RESET_MATCH');
			break;
		}
		break;
	case 'setup_match_btn':
		document.tennis_form.method = 'post';
		document.tennis_form.action = 'setup';
	   	document.tennis_form.submit();
		break;
	case 'load_match_btn':
		processWaitingButtonSpinner('START_WAIT_TIMER');
		processTennisProcedures('LOAD_MATCH',$('#select_tennis_matches option:selected'));
		break;
	case 'log_saves_btn':
		processTennisProcedures('SAVES',match_data);
		break;
	case 'cancel_undo_btn': case 'cancel_overwrite_btn': case 'cancel_event_btn': case 'cancel_saves_btn':
		document.getElementById('select_event_div').style.display = 'none';
		addItemsToList('LOAD_EVENTS',match_data); 
		processWaitingButtonSpinner('END_WAIT_TIMER');
		break;
	case 'select_existing_tennis_matches':
		if(whichInput.value.toLowerCase().includes('new_match')) {
			initialiseForm('SETUP',null);
		} else {
			processWaitingButtonSpinner('START_WAIT_TIMER');
			processTennisProcedures('LOAD_SETUP',$('#select_existing_tennis_matches option:selected'));
		}
		break;
	default:
		if(whichInput) {
			if(whichInput.id.includes('_score_btn')) { 
				error_msg = 'Cannot find any started set. Please start a set first before logging an event';
				match_data.sets.forEach(function(set,set_index,set_arr){
					if(set.set_status.toLowerCase() == 'start') {
						error_msg = '';
					}
				});
				if(error_msg) {
					alert(error_msg);
					return false;			
				} else {
					error_msg = 'Cannot find any started game. Please start a game first before logging an event';
					match_data.sets.forEach(function(set,set_index,set_arr){
						if(set.set_status.toLowerCase() == 'start') {
							set.games.forEach(function(game,game_index,game_arr){
								if(game.game_status.toLowerCase() == 'start') {
									error_msg = '';
								}
							});
						}
					});
					if(error_msg) {
						alert(error_msg);
						return false;			
					} else {
						processWaitingButtonSpinner('START_WAIT_TIMER');
						processTennisProcedures('LOG_SCORE',whichInput);
					}
				}
			}
		}
		break;
	}
}
function processTennisProcedures(whatToProcess, whichInput)
{
	var value_to_process; 
	
	switch(whatToProcess) {
	case 'LOG_SET': case 'LOG_GAME':
		if(whichInput == 'START' || whichInput == 'RESET') {
			value_to_process = whichInput;
			switch(whatToProcess) {
			case 'LOG_GAME':
				if(whichInput == 'START') {
					value_to_process = value_to_process + ',' + $('#select_scoring_type option:selected').val();
				}
				break;
			}
			
		}else if(whichInput == 'END') {
			switch(whatToProcess) {
			case 'LOG_SET': 
				value_to_process = whichInput + ',' + $('#select_set_winner option:selected').val();
				break;
			case 'LOG_GAME':
				value_to_process = whichInput + ',' + $('#select_game_winner option:selected').val();
				break;
			}
		}
		break;
	case 'LOAD_MATCH': case 'LOAD_SETUP':
		value_to_process = whichInput.val();
		break;
	case 'LOG_SCORE': 
		value_to_process = whichInput.id;
		break;
	case 'LOG_SERVE':
		value_to_process = $('#select_serving_player option:selected').val();
		break;
	case 'UNDO':
		value_to_process = $('#number_of_undo_txt').val();
		break;
	}

	$.ajax({    
        type : 'Get',     
        url : 'processTennisProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + value_to_process, 
        dataType : 'json',
        success : function(data) {
			match_data = data;
        	switch(whatToProcess) {
    		case 'UNDO':
        		addItemsToList('LOAD_MATCH',data);
				addItemsToList('LOAD_EVENTS',data);
				document.getElementById('select_event_div').style.display = 'none';
        		break;
			case 'LOG_SCORE': case 'LOAD_MATCH': case 'LOG_SET': case 'LOG_GAME':
        		addItemsToList('LOAD_MATCH',data);
				switch(whatToProcess) {
				case 'LOG_SCORE':
					if($('#select_scoring_type option:selected').val() == 'normal') {
						processVariousStats('CHECK-GAME-WINNER');
					}else if($('#select_scoring_type option:selected').val() == 'tie_break') {
						processVariousStats('CHECK-TIE-BREAK-WINNER');
					}
					break;
				case 'LOAD_MATCH':
					addItemsToList('LOAD_EVENTS',data);
					document.getElementById('tennis_div').style.display = '';
					document.getElementById('select_event_div').style.display = '';
					break;
				case 'LOG_GAME':
					if(whichInput == 'START') {
						processUserSelection($('#select_serving_player'));
					}else if(whichInput == 'END') {
						processVariousStats('CHECK-SET-WINNER');
					}
					break;
				}
        		break;
        	case 'LOAD_SETUP':
        		initialiseForm('SETUP',data);
        		break;
        	}
    		processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function processVariousStats(whatToProcess)
{
	switch(whatToProcess){
	case 'CHECK-SET-WINNER':
		var home_game_wins = 0, away_game_wins = 0;
		match_data.sets.forEach(function(set,set_index,set_arr){
			if(set.set_status.toLowerCase() == 'start') {
				set.games.forEach(function(game,game_index,game_arr){
					if(game.game_winner.toLowerCase() == 'home') {
						home_game_wins = home_game_wins + 1;
					}else if(game.game_winner.toLowerCase() == 'away') {
						away_game_wins = away_game_wins + 1;
					}
				});
			}
		});
		if(home_game_wins >= 6 && (home_game_wins - away_game_wins) >= 2) {
			$('#select_set_winner').val('home');
			processUserSelection($('#end_set_btn'));
		}else if(away_game_wins >= 6 && (away_game_wins - home_game_wins) >= 2) {
			$('#select_set_winner').val('away');
			processUserSelection($('#end_set_btn'));
		}
		break;
	case 'CHECK-GAME-WINNER':
		if($('#homeScore').val() == 'GAME' || $('#awayScore').val() == 'GAME') {
			if($('#homeScore').val() == 'GAME') {
				$('#select_game_winner').val('home');
			} else if ($('#awayScore').val() == 'GAME') {
				$('#select_game_winner').val('away');
			}
			processUserSelection($('#end_game_btn'));
		}
		break;
	case 'CHECK-TIE-BREAK-WINNER':
		if(parseInt($('#homeScore').val()) >= 7 || parseInt($('#awayScore').val()) >= 7) {
			if(parseInt($('#homeScore').val()) >= 7 && (parseInt($('#homeScore').val()) - parseInt($('#awayScore').val())) >= 2) {
				$('#select_game_winner').val('home');
				processUserSelection($('#end_game_btn'));
			} else if (parseInt($('#awayScore').val()) >= 7 && (parseInt($('#awayScore').val()) - parseInt($('#homeScore').val())) >= 2) {
				$('#select_game_winner').val('away');
				processUserSelection($('#end_game_btn'));
			}
		}
		break;
	}
}
function addItemsToList(whatToProcess, dataToProcess)
{
	var max_cols,div,anchor,row,header_text,select,option,tr,th,thead,text,table,tbody;
	
	switch (whatToProcess) {
	case 'LOAD_UNDO':

		$('#select_event_div').empty();
		
		if(dataToProcess.events.length > 0) {

			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
					
			tbody = document.createElement('tbody');
			row = tbody.insertRow(tbody.rows.length);
			
			select = document.createElement('select');
			select.id = 'select_undo';
			dataToProcess.events = dataToProcess.events.reverse();
			var max_loop = dataToProcess.events.length;
			if(max_loop > 5) {
				max_loop = 5;
			}
			for(var i = 0; i < max_loop; i++) {
				option = document.createElement('option');
				option.value = dataToProcess.events[i].eventNumber;
			    option.text = dataToProcess.events[i].eventNumber + '. ' + dataToProcess.events[i].eventType;
			    select.appendChild(option);
			}
			header_text = document.createElement('label');
			header_text.innerHTML = 'Last Five Events: '
			header_text.htmlFor = select.id;
			row.insertCell(0).appendChild(header_text).appendChild(select);

		    option = document.createElement('input');
		    option.type = 'text';
		    option.name = 'number_of_undo_txt';
		    option.value = '1';
		    option.id = option.name;
		    option.setAttribute('onblur','processUserSelection(this)');
			header_text = document.createElement('label');
			header_text.innerHTML = 'Number of undos: '
			header_text.htmlFor = option.id;
			row.insertCell(1).appendChild(header_text).appendChild(option);
			
		    div = document.createElement('div');

		    option = document.createElement('input');
		    option.type = 'button';
		    option.name = 'log_undo_btn';
		    option.id = option.name;
		    option.value = 'Undo Last Event';
		    option.setAttribute('onclick','processUserSelection(this);');
		    
		    div.append(option);

			option = document.createElement('input');
			option.type = 'button';
			option.name = 'cancel_undo_btn';
			option.id = option.name;
			option.value = 'Cancel';
			option.setAttribute('onclick','processUserSelection(this)');

		    div.append(document.createElement('br'));
		    div.append(option);

		    row.insertCell(2).appendChild(div);

			table.appendChild(tbody);
			document.getElementById('select_event_div').appendChild(table);

		} else {
			return false;
		}
		
		break;
	
	case 'LOAD_EVENTS':
		
		$('#select_event_div').empty();

   		div = document.createElement('div');
		div.style = 'text-align:center;';
		
	    option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'start_set_btn';
	    option.value = 'Start Set';
	    option.id = option.name;
	    option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		select = document.createElement('select');
		select.id = 'select_set_winner';
		select.style = 'width:175px;';
		option = document.createElement('option');
		option.value = 'home';	
		if(match_data.matchType.toLowerCase() == 'doubles' && match_data.homeSecondPlayerId > 0) {
			option.text = match_data.homeFirstPlayer.full_name + '/' + match_data.homeSecondPlayer.full_name;
		} else {
			option.text = match_data.homeFirstPlayer.full_name;
		}
		select.appendChild(option);
		option = document.createElement('option');
		option.value = 'away';	
		if(match_data.matchType.toLowerCase() == 'doubles' && match_data.awaySecondPlayerId > 0) {
			option.text = match_data.awayFirstPlayer.full_name + '/' + match_data.awaySecondPlayer.full_name;
		} else {
			option.text = match_data.awayFirstPlayer.full_name;
		}
		select.appendChild(option);
		
		text = document.createElement('label');
		text.innerHTML = 'Set Winner: '
		text.htmlFor = select.id;
		document.getElementById('select_event_div').appendChild(text).appendChild(select);

	    option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'end_set_btn';
	    option.value = 'End Set';
	    option.id = option.name;
	    option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

	    option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'reset_set_btn';
	    option.value = 'RESET Set';
	    option.id = option.name;
	    option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		document.getElementById('select_event_div').appendChild(div);

   		div = document.createElement('div');
		div.style = 'text-align:center;';

	    option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'start_game_btn';
	    option.value = 'Start Game';
	    option.id = option.name;
	    option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		select = document.createElement('select');
		select.id = 'select_game_winner';
		select.style = 'width:175px;';
		option = document.createElement('option');
		option.value = 'home';	
		if(match_data.matchType.toLowerCase() == 'doubles' && match_data.homeSecondPlayerId > 0) {
			option.text = match_data.homeFirstPlayer.full_name + '/' + match_data.homeSecondPlayer.full_name;
		} else {
			option.text = match_data.homeFirstPlayer.full_name;
		}
		select.appendChild(option);
		option = document.createElement('option');
		option.value = 'away';	
		if(match_data.matchType.toLowerCase() == 'doubles' && match_data.awaySecondPlayerId > 0) {
			option.text = match_data.awayFirstPlayer.full_name + '/' + match_data.awaySecondPlayer.full_name;
		} else {
			option.text = match_data.awayFirstPlayer.full_name;
		}
		select.appendChild(option);
		
		text = document.createElement('label');
		text.innerHTML = 'Game Winner: '
		text.htmlFor = select.id;
		document.getElementById('select_event_div').appendChild(text).appendChild(select);

	    option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'end_game_btn';
	    option.value = 'End Game';
	    option.id = option.name;
	    option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

	    option = document.createElement('input');
	    option.type = 'button';
	    option.name = 'reset_game_btn';
	    option.value = 'RESET Game';
	    option.id = option.name;
	    option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		document.getElementById('select_event_div').appendChild(div);
				
		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
		tbody = document.createElement('tbody');
        for(var i=0; i<=0; i++) {
            row = tbody.insertRow(tbody.rows.length);
    		for(var j=0; j<=2; j++) {
        		div = document.createElement('div');
   				div.style = 'text-align:center;';
				switch (j) {
				case 0: case 2:
					header_text = document.createElement('label');
	    			switch (j) {
	    			case 0: //home
						text = 'home';
						if(match_data.matchType.toLowerCase() == 'doubles' && match_data.homeSecondPlayerId > 0) {
							header_text.innerHTML = match_data.homeFirstPlayer.full_name + '/' + match_data.homeSecondPlayer.full_name;
						} else {
							header_text.innerHTML = match_data.homeFirstPlayer.full_name;
						}
	    				break;
	    			case 2: //away
						text = 'away';
						if(match_data.matchType.toLowerCase() == 'doubles' && match_data.awaySecondPlayerId > 0) {
							header_text.innerHTML = match_data.awayFirstPlayer.full_name + '/' + match_data.awaySecondPlayer.full_name;
						} else {
							header_text.innerHTML = match_data.awayFirstPlayer.full_name;
						}
	    				break;
					}
					div.appendChild(header_text);
	        		link_div = document.createElement('div');
					for(var k=0; k<=2; k++) {
						switch (k) {
						case 0: case 2:
		        			option = document.createElement('input');
		    				option.type = "button";
		    				if (k == 0) {
		        				option.id = text + '_increment_score_btn';
		        				option.value="+";
		        				option.setAttribute('onclick','processUserSelection(this);');
		        				}
		    				else {
								option.id = text + '_decrement_score_btn';
								option.value="-";
								option.setAttribute('onclick','processUserSelection(this);');
								break;
							}
		    				option.style = 'text-align:center;';
							break;
						case 1: 
		        			option = document.createElement('input');
		    				option.type = "text";
		    				option.id = text + 'Score';
		    				option.style = 'width:25%;text-align:center;';
							option.value = '0';
							match_data.sets.forEach(function(set,set_index,set_arr){
								set.games.forEach(function(game,game_index,game_arr){
									switch(j) {
									case 0:
										option.value = game.home_score;
										break;
									case 2:
										option.value = game.away_score;
										break;
									}
								});
							});
		    				break;
						}
						link_div.appendChild(option);
				    }	
					div.appendChild(link_div);
				    break;
				
				case 1:

					select = document.createElement('select');
					select.id = 'select_scoring_type';
					select.style = 'width:175px;';
					
					option = document.createElement('option');
					option.value = 'normal';	
					option.text = 'Normal';
					select.appendChild(option);
					
					option = document.createElement('option');
					option.value = 'tie_break';	
					option.text = 'Tie Breaker';
					select.appendChild(option);

					text = document.createElement('label');
					text.innerHTML = 'Score Type: '
					text.htmlFor = select.id;

					div.appendChild(text).appendChild(select);
					
					break;		
			    }
			    row.insertCell(j).appendChild(div);
	     	 }	 
	    }		
		table.appendChild(tbody);
		
		document.getElementById('select_event_div').appendChild(table);

		if(match_data) {
			match_data.sets.forEach(function(set,set_index,set_arr){
				set.games.forEach(function(game,game_index,game_arr){
					if(game.game_status.toLowerCase() == 'start') {
						$('#select_scoring_type').val(game.game_type);
					}
				});
			});
		}
		
		option = document.createElement('select');
		option.id = 'select_serving_player';
		option.name = option.id;
		option.style = 'width:175px;';
		text = document.createElement('option');
		text.value = 'home';	
		if(match_data.matchType.toLowerCase() == 'doubles' && match_data.homeSecondPlayerId > 0) {
			text.text = match_data.homeFirstPlayer.full_name + '/' + match_data.homeSecondPlayer.full_name;
		} else {
			text.text = match_data.homeFirstPlayer.full_name;
		}
		option.appendChild(text);
		text = document.createElement('option');
		text.value = 'away';	
		if(match_data.matchType.toLowerCase() == 'doubles' && match_data.awaySecondPlayerId > 0) {
			text.text = match_data.awayFirstPlayer.full_name + '/' + match_data.awaySecondPlayer.full_name;
		} else {
			text.text = match_data.awayFirstPlayer.full_name;
		}
		option.appendChild(text);
		option.setAttribute('onchange','processUserSelection(this);');
		option.appendChild(text);
		
		text = document.createElement('label');
		text.innerHTML = 'Select Serve: '
		text.htmlFor = option.id;
		document.getElementById('select_event_div').appendChild(text).appendChild(option);
		
		break;
				
	case 'LOAD_MATCH':
		
		$('#tennis_div').empty();
		
		if (dataToProcess)
		{
			table = document.createElement('table');
			table.setAttribute('class', 'table table-bordered');
			tbody = document.createElement('tbody');
			row = tbody.insertRow(tbody.rows.length);
			for (var j = 1; j <= (match_data.sets.length + 1); j++) {
				cell = row.insertCell(row.cells.length);
			    th = document.createElement('th'); //column
			    switch (j) {
				case 1:
				    cell.innerHTML = ''; 
					break;
				default:
				    cell.innerHTML = match_data.sets[j-2].set_number; 
					break;
				}
			}
						
			for (var j = 0; j <= 1; j++) {
				row = tbody.insertRow(tbody.rows.length);
				cell = row.insertCell(row.cells.length);
			    switch (j) {
				case 0:
				    cell.innerHTML = match_data.homeFirstPlayer.full_name;
					if(match_data.matchType.toLowerCase() == 'doubles' && match_data.homeSecondPlayerId > 0) {
					    cell.innerHTML = cell.innerHTML + '/' + match_data.homeSecondPlayer.full_name;
					}
					match_data.sets.forEach(function(set,set_index,set_arr){
						text = 0; 
						set.games.forEach(function(game,game_index,game_arr){
							if(game.game_winner.toLowerCase() == 'home') {
								text = text + 1;
							}
							if(game.game_status.toLowerCase() == 'start') {
								$('#homeScore').val(game.home_score);
							}
						});
						cell = row.insertCell(row.cells.length);
						cell.innerHTML = text;
					});
					break;
				case 1:
				    cell.innerHTML = match_data.awayFirstPlayer.full_name;
					if(match_data.matchType.toLowerCase() == 'doubles' && match_data.awaySecondPlayerId > 0) {
					    cell.innerHTML = cell.innerHTML + '/' + match_data.awaySecondPlayer.full_name;
					}
					match_data.sets.forEach(function(set,set_index,set_arr){
						text = 0; 
						set.games.forEach(function(game,game_index,game_arr){
							if(game.game_winner.toLowerCase() == 'away') {
								text = text + 1;
							}
							if(game.game_status.toLowerCase() == 'start') {
								$('#awayScore').val(game.away_score);
							}
						});
						cell = row.insertCell(row.cells.length);
						cell.innerHTML = text;
					});
					break;
				}
			}
			table.appendChild(tbody);
			document.getElementById('tennis_div').appendChild(table);
		}
	break;
	}
}
function removeSelectDuplicates(select_id)
{
	var this_list = {};
	$("select[id='" + select_id + "'] > option").each(function () {
	    if(this_list[this.text]) {
	        $(this).remove();
	    } else {
	        this_list[this.text] = this.value;
	    }
	});
}
function checkEmpty(inputBox,textToShow) {

	var name = $(inputBox).attr('id');
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(document.getElementById(name).value.trim() == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}	
