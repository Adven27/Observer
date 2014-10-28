package com.example.prototype.dao;

import java.util.Collection;

/**
 * ��������� ��� ���� ��������, ����������� ������ � ������.
 * @author victor
 *
 */
public interface IDAO {
	
	public <T> void save(T objToSave);

	public <T> Collection<T> getAll(Class<T> type);

	public <T> T get(Class<T> type, long id);
}