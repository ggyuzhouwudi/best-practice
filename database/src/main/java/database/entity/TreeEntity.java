package database.entity;

import java.io.Serializable;

import base.model.Tree;

/**
 * 树形数据模型，序列号自动初始化以及所有下级节点序列号的自动更新
 *
 * @param <ID> 主键类型
 * @param <T>  子节点类型
 * @author Oliver
 * @date 2021/7/5
 * @see BasicTreeEntity
 * @see BasicTenantTreeEntity
 */
public interface TreeEntity<ID extends Serializable, T extends TreeEntity<ID, T>> extends
    Tree<ID, T>, Entity<ID> {

    /**
     * 获取序列号
     *
     * @return 序列号
     */
    String getSequence();

    /**
     * 设置序列号
     *
     * @param sequence 序列号
     */
    void setSequence(String sequence);
}
