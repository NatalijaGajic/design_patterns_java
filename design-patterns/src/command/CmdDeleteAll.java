package command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO onaj koji se selektuje prvi se prvi vraca u listu, bude iznad a mozda treba da bude ispod
// zbog toga sortiranje
public class CmdDeleteAll implements Command, Serializable {

	private List<CmdDelete> listOfDeleteCommands = new ArrayList<CmdDelete>();
	
	public CmdDeleteAll () {
			
	}
	
	@Override
	public void execute() {
		sortByIndex();
		CmdDelete deleteCommand;
		Iterator<CmdDelete> it = listOfDeleteCommands.iterator();
		while(it.hasNext()) {
			deleteCommand = it.next();
			deleteCommand.execute();
		}
	}

	@Override
	public void unexecute() {
		sortByIndex();
		CmdDelete deleteCommand;
		Iterator<CmdDelete> it = listOfDeleteCommands.iterator();
		while(it.hasNext()) {
			deleteCommand = it.next();
			deleteCommand.unexecute();
		}
	}
	
	public void add(CmdDelete cmd) {
		listOfDeleteCommands.add(cmd);
	}

	public String toString() {
		StringBuilder compositionOfStrings = new StringBuilder("");
		Iterator<CmdDelete> it = listOfDeleteCommands.iterator();
		compositionOfStrings.append(it.next().toString());
		while(it.hasNext()) {
			compositionOfStrings.append(";");
			compositionOfStrings.append(it.next().toString());
		}
		return compositionOfStrings.toString();
	}
	
	//edge case: add rect1, add rect2, select rect2, select rect1, pokusava prvo da vrati u listu
	//shape-ova rect2 na indeks 1, a lista ima velicinu 0, zbog toga sortiramo
	//da se prvo u listu ubaci rect1 pa rect2 na indeks 1
	//TODO treba prvo da se izbrise donji pa gornji, da bi pri undo prvo dodao donji pa gornji
	private void sortByIndex() {
		CmdDelete deleteCommand;
		CmdDelete[] array = new CmdDelete[listOfDeleteCommands.size()];
		int i=0;
		Iterator<CmdDelete> it = listOfDeleteCommands.iterator();
		while(it.hasNext()) {
			deleteCommand = it.next();
			array[i]=deleteCommand;
			i++;
		}
		bubbleSort(array);
		i=0;
		listOfDeleteCommands.removeAll(listOfDeleteCommands);
		for(;i<array.length;i++) {
			listOfDeleteCommands.add(array[i]);
		}
	}
	
	void bubbleSort(CmdDelete arr[]) 
    { 
        int n = arr.length; 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr[j].getIndex() > arr[j+1].getIndex()) 
                {
                    CmdDelete temp = arr[j]; 
                    arr[j] = arr[j+1]; 
                    arr[j+1] = temp; 
                } 
    } 
	
	
	public boolean equals (Object o) {
		if(o instanceof CmdDeleteAll) {
			CmdDeleteAll cda = (CmdDeleteAll)o;
			if(cda.getListOfDeleteCommands().size() == getListOfDeleteCommands().size()) {
				Iterator<CmdDelete> it = getListOfDeleteCommands().iterator();
				Iterator<CmdDelete> it2 = getListOfDeleteCommands().iterator();
				while(it.hasNext() && it2.hasNext()) {
					if(!it.next().equals(it2.next())) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public ArrayList<CmdDelete> getListOfDeleteCommands(){
		return (ArrayList<CmdDelete>) listOfDeleteCommands;
	}
}
