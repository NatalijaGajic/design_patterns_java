package mvc;

public class SavingManager implements Saving{

	private Saving saving;
	public SavingManager(Saving saving) {
		this.saving = saving;
	}
	@Override
	public void save() {
		saving.save();
	}
	
	public void setSaving(Saving saving) {
		this.saving = saving;
	}

}
