package kr.co.namsang.mb.barista.data;

public class IndexPath extends Object {
	
	public final Integer section;		
	public final Integer row;
	
	public IndexPath(int section, int row) {
		super();
		this.section = section;
		this.row = row;		
	}
	
	public static IndexPath create(Integer section, Integer row) {
		return new IndexPath(section, row);
	}
	
	public boolean equals(Object o) {
		if (o instanceof IndexPath) {
			IndexPath other = (IndexPath)o;
			if (this.section == other.section
					&& this.row == other.row)
				return true;
		}
		
		return false;
	}
}
