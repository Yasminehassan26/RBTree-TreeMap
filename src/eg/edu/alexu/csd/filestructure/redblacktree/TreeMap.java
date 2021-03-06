package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap {

	private RedBlackTree<T, V> redBlackTree = new RedBlackTree<T, V>();
	private int size = 0;

	@Override
	public Map.Entry ceilingEntry(Comparable key) {
		INode temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
		boolean flag = false;
		if (temp==null) {
			put(key,null);
			temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
			flag=true;
		}
		if(temp.getRightChild()!=null) {
			temp= temp.getRightChild();
			return new MapEntry<T, V>((T) temp.getKey(), (V) temp.getValue());
		}
		if(flag==true) {
			remove(key);
			return null;
		}
		return new MapEntry<T, V>((T) temp.getKey(), (V) temp.getValue());
		
		
	}

	@Override
	public Comparable ceilingKey(Comparable key) {
		INode temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
		boolean flag = false;
		if (temp==null) {
			put(key,null);
			temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
			flag=true;
		}
		if(temp.getRightChild()!=null) {
			temp= temp.getRightChild();
			return temp.getKey();
		}
		if(flag==true) {
			remove(key);
			return null;
		}
		return temp.getKey();
	}

	@Override
	public void clear() {
		INode node = this.redBlackTree.getRoot();
		clearNode(node);

	}
	
	private void clearNode(INode node) {
		if(node==null)
			return;
		clearNode(node.getLeftChild());
		clearNode(node.getRightChild());
		remove(node.getKey());
	}

	@Override
	public boolean containsKey(Comparable key) {
		Object object = this.redBlackTree.search(key);
		if(object==null)
			return false;
		return true;
	}

	@Override
	public boolean containsValue(Object value) {
		Collection<Object> col = values();
		return col.contains(value);
	}
	

	@Override
	public Set<Map.Entry> entrySet() {
		Set<Map.Entry> set = new HashSet<Map.Entry>();
		INode node = this.redBlackTree.getRoot();
		addEntryToSet(node,set);
		return set;
	}
	
	private void addEntryToSet (INode node,Set<Map.Entry> set) {
		if(node==null)
			return;
		addEntryToSet(node.getLeftChild(),set);
		addEntryToSet(node.getRightChild(),set);
		set.add(new MapEntry<T, V>((T) node.getKey(), (V) node.getValue()));
	}

	private INode getLeastKey(INode root) {
		if (root.getLeftChild().isNull()) {
			return root;
		}
		return getLeastKey(root.getLeftChild());
	}

	@Override
	public Map.Entry firstEntry() {
		if (!this.redBlackTree.isEmpty()) {
			INode leastKeyNode = getLeastKey(this.redBlackTree.getRoot());
			Map.Entry<T, V> entry = new MapEntry<T, V>((T) leastKeyNode.getKey(), (V) leastKeyNode.getValue());
			return entry;
		}
		return null;
	}

	@Override
	public Comparable firstKey() {
		if (!this.redBlackTree.isEmpty()) {
			INode node = getLeastKey(this.redBlackTree.getRoot());
			return node.getKey();
		}
		return null;
	}

	@Override
	public Map.Entry floorEntry(Comparable key) {
		INode temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
		boolean flag = false;
		if (temp==null) {
			put(key,null);
			temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
			flag=true;
		}
		if(temp.getLeftChild()!=null) {
			temp= temp.getLeftChild();
			return new MapEntry<T, V>((T) temp.getKey(), (V) temp.getValue());
		}
		if(flag==true) {
			remove(key);
			return null;
		}
		return new MapEntry<T, V>((T) temp.getKey(), (V) temp.getValue());
	}

	@Override
	public Comparable floorKey(Comparable key) {
		INode temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
		boolean flag = false;
		if (temp==null) {
			put(key,null);
			temp = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), key);
			flag=true;
		}
		if(temp.getLeftChild()!=null) {
			temp= temp.getLeftChild();
			return temp.getKey();
		}
		if(flag==true) {
			remove(key);
			return null;
		}
		return temp.getKey();
	}

	@Override
	public Object get(Comparable key) {
		if (this.redBlackTree.isEmpty()) {
			return null;
		}
		Object value = this.redBlackTree.search(key);
		return value;
	}

	@Override
	public ArrayList<Map.Entry> headMap(Comparable toKey) {
		ArrayList<Map.Entry> headMapArray = new ArrayList<Map.Entry>();
		INode node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
		boolean flag= false;
		if (node == null) {
			put(toKey,null);
			node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
			flag=true;
		}
		
		headMapAdd(headMapArray, node.getLeftChild());
		if(flag==true)
			remove(toKey);
		return headMapArray;
	}

	private void headMapAdd(ArrayList<Map.Entry> array, INode node) {
		if (node == null)
			return;
		if (node.getLeftChild() != null)
			headMapAdd(array, node.getLeftChild());
		Map.Entry<T, V> entry = new MapEntry<T, V>((T) node.getKey(), (V) node.getValue());
		array.add(entry);
		if (node.getLeftChild() != null)
			headMapAdd(array, node.getRightChild());

	}

	@Override
	public ArrayList<Map.Entry> headMap(Comparable toKey, boolean inclusive) {
		ArrayList<Map.Entry> headMapArray = new ArrayList<Map.Entry>();
		INode node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
		boolean flag= false;
		if (node == null) {
			put(toKey,null);
			node = this.redBlackTree.searchForNode(this.redBlackTree.getRoot(), toKey);
			flag=true;
		}
		if(flag==true) {
			headMapAdd(headMapArray, node.getLeftChild());
			remove(toKey);
		}
		else
			headMapAdd(headMapArray, node);
		return headMapArray;
	}

	@Override
	public Set keySet() {
		Set<Comparable> set = new HashSet<Comparable>();
		INode node = this.redBlackTree.getRoot();
		addKeyToSet(node,set);
		return set;
		
		
	}
	private  void addKeyToSet(INode node,Set<Comparable> set) {
		if(node==null)
			return;
		addKeyToSet(node.getLeftChild(),set);
		addKeyToSet(node.getRightChild(),set);
		set.add(node.getKey());
	}

	private INode lastEntryNode(INode node) {
		if (node.getRightChild().isNull()) {
			return node;
		}
		return lastEntryNode(node.getRightChild());
	}

	@Override
	public Map.Entry lastEntry() {
		if (this.redBlackTree.isEmpty()) {
			return null;
		}
		INode node = lastEntryNode(this.redBlackTree.getRoot());
		return new MapEntry<T, V>((T) node.getKey(), (V) node.getValue());
	}

	@Override
	public Comparable lastKey() {
		if (this.redBlackTree.isEmpty()) {
			return null;
		}
		INode node = lastEntryNode(this.redBlackTree.getRoot());
		return node.getKey();
	}

	@Override
	public Map.Entry pollFirstEntry() {
		Map.Entry<T, V> entry = firstEntry();
		if (entry != null) {
			this.redBlackTree.delete(entry.getKey());
		}
		return entry;
	}

	@Override
	public Map.Entry pollLastEntry() {
		Map.Entry<T, V> lastEntry = lastEntry();
		if (lastEntry != null) {
			this.redBlackTree.delete(lastEntry.getKey());
		}
		return lastEntry;
	}

	@Override
	public void put(Comparable key, Object value) {
		this.redBlackTree.insert(key, value);
		this.size++;
	}

	@Override
	public void putAll(Map map) {
		Set<Map.Entry<T, V>> enrtyset = map.entrySet();
		Map.Entry<T, V>[] entries = (Map.Entry<T, V>[]) enrtyset.toArray();
		for (int i = 0; i < entries.length; i++) {
			Map.Entry<T, V> entry = entries[i];
			this.redBlackTree.insert(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public boolean remove(Comparable key) {
		return false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public Collection values() {
		Collection<Object> col = new ArrayList<Object>();
		INode node = this.redBlackTree.getRoot();
		addValueToCol(node,col);
		return col;
	}
	
	
	private void addValueToCol (INode node,Collection<Object> col) {
		if(node==null)
			return;
		addValueToCol(node.getLeftChild(),col);
		addValueToCol(node.getRightChild(),col);
		col.add(node.getValue());
	}

	public RedBlackTree<T, V> getRedBlackTree() {
		return redBlackTree;
	}

	public void setRedBlackTree(RedBlackTree<T, V> redBlackTree) {
		this.redBlackTree = redBlackTree;
	}
}
