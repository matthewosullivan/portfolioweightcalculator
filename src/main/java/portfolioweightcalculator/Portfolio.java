package portfolioweightcalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Portfolio {
	
	private static final Logger logger = Logger.getLogger(Portfolio.class.getName());
	
	Map<String,TreeNode> treeNodes = new HashMap<>();
	
	public Portfolio buildFromFile(String filename) {
		try {
			Files.lines(Paths.get(filename)).collect(Collectors.toSet()).forEach(line -> {
				String[] values = line.split(",");
				build(values[0], values[1], Integer.parseInt(values[2]));
			});
		} catch (IOException e) {
			logger.log(Level.WARNING, "Failed to read fund structure from csv file", e);
		}
		return this;
	}
	
	public Portfolio build(String parentFundId, String childFundId, int marketValue) {
		treeNodes.putIfAbsent(parentFundId, new TreeNode(parentFundId));
		treeNodes.putIfAbsent(childFundId, new TreeNode(childFundId));
		treeNodes.get(childFundId).setMarketValue(marketValue);
		treeNodes.get(parentFundId).getChildren().add(treeNodes.get(childFundId));
		return this;
	}
	
	private void calculate(TreeNode treeNode, double parentWeight, TreeNode root, List<String> output) {
		if (treeNode.isLeaf()) {
			output.add(root.getId() + ',' + treeNode.getId() + "," + String.format("%.3f", parentWeight));
		} else {
			double aggregateMarketValue = treeNode.getAggregateMarketValue();
			for (TreeNode child : treeNode.getChildren()) {
				double newValue = (child.getMarketValue()/aggregateMarketValue) * parentWeight;
				calculate(child, newValue, root, output);
			}
		}
	}
	
	public List<String> calculateFundWeight() {
		List<TreeNode> parents = treeNodes.values().stream().filter(n -> n.isParent()).collect(Collectors.toList());
		List<String> output = new ArrayList<>();
		for (TreeNode treeNode : parents) {
			calculate(treeNode, 1.0, treeNode, output);
		}
		return output;
	}
	
}
