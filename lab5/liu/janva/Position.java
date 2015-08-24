package liu.janva;


//TODO does java have something more appopriate like 
// countable discrete numbers )
//make generic maybe even need create IndexTyp
public class Position 
{
		private int row;
		private int col;

		public Position( int row,int col) 
		{
			this.row= row;
			this.col= col;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}
}

