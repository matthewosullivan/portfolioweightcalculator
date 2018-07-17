package portfolioweightcalculator;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private String id;
	private int marketValue;
	private List<TreeNode> children = new ArrayList<>();
	
	public TreeNode(String id) {
		this.id = id;
	}
	
	public boolean isParent() {
		return !getChildren().isEmpty();
	}
	
	public boolean isLeaf() {
		return getChildren().isEmpty();
	}
	
	public int getAggregateMarketValue() {
		return getChildren().stream().mapToInt(TreeNode::getMarketValue).sum();
	}
	
	public int getMarketValue() {
		return marketValue;
	}
	
	public void setMarketValue(int marketValue) {
		this.marketValue = marketValue;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		getChildren().forEach(n -> sb.append(n.id + " (MV=" + n.getMarketValue() + "),"));
		return id + " --> [" + sb.toString().substring(0, Math.max(sb.toString().length()-1, 0)) + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TreeNode) {
			return id.equals(((TreeNode)obj).id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public String getId() {
		return id;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
}
