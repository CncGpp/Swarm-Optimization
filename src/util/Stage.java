package util;

import java.util.List;

import util.Gloabal.R;

public class Stage {
	private final List<String> stageList = R.STAGE_LIST;

	private int currStage = 0;

	public Stage(int currStage) {
		if(currStage >= stageList.size()) currStage = 0;
		this.currStage = currStage;
	}

	public Stage() {
		this(0);
	}

	public int getStageNumber() {return currStage;}
	public String getStagePath() {return stageList.get(currStage);}
	public boolean nextStage() {
		if(currStage + 1 == stageList.size()) return false;
		else currStage++;
		return true;
	}

}
