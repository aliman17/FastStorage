package data;

public class Entity implements Comparable<Entity> {
	
	public String matchId;
	public int marketId;
	public String outputId;
	public String specifications;
	
	public Entity(String matchId, int marketId, String outputId, String specifications) {
		this.matchId = matchId;
		this.marketId = marketId;
		this.outputId = outputId;
		this.specifications = specifications;
	}
	
	public boolean equals(Entity o) {
		return this.matchId.equals(o.matchId) && 
				this.marketId == o.marketId && 
				this.outputId.equals(o.outputId) &&
				this.specifications.equals(o.specifications);
	}

	@Override
	public int compareTo(Entity o) {
		int matchCmp = this.matchId.compareTo(o.matchId);
		if (matchCmp != 0) 
			return matchCmp;
		
		if (this.marketId != o.marketId) 
			return (this.marketId < o.marketId) ? -1 : 1;
		
		int outputCmp = this.outputId.compareTo(o.outputId);
		if (outputCmp != 0)
			return outputCmp;
		
		int specCmp = this.specifications.compareTo(o.specifications);
		return specCmp;
	}
	
	public String toString() {
		return "{" + matchId + ", " + marketId + ", " + outputId + ", " + specifications + "}";
	}
}
