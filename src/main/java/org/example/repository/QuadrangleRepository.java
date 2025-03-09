package org.example.repository;

import org.example.exceptions.RepositoryDataException;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;

import java.util.Comparator;
import java.util.List;
//Не все методы в интерфейсе QuadrangleRepository должны бросать исключения. Давайте разберём, какие методы действительно нуждаются в этом, и почему:
//
//Методы, которые должны бросать исключения
//add(Quadrangle quadrangle)
//
//Проверяет существование квадрангла в репозитории.
//Если такой квадрангл уже существует, выбрасывается RepositoryDataException.
//Исключение нужно.
//remove(Quadrangle quadrangle)
//
//Проверяет, существует ли указанный квадрангл.
//Если его нет, выбрасывается RepositoryDataException.
//Исключение нужно.
//update(Quadrangle quadrangle)
//
//Проверяет существование квадрангла по ID.
//Если квадрангл не найден, выбрасывается RepositoryDataException.
//Исключение нужно.
//Методы, которые не обязательно должны бросать исключения
//query(Specification<Quadrangle> specification)
//
//Этот метод фильтрует данные из репозитория.
//Он работает с уже существующими объектами в хранилище и не изменяет их.
//Исключение не нужно.
//query(Specification<Quadrangle> specification, Comparator<Quadrangle> comparator)
//
//Добавляет сортировку к результату фильтрации.
//Также работает только с уже существующими объектами.
//Исключение не нужно.
public interface QuadrangleRepository {
    void add(Quadrangle quadrangle) throws RepositoryDataException;     //ДОЛЖЕН ПРОВЕРЯТЬ НА СУЩЕСТВОВАНИЕ Quadrangle, если уже есть, то кидать QuadrangleRepositoryException
    void remove(Quadrangle quadrangle) throws RepositoryDataException;  //если нету, тоже кидать
    void update(Quadrangle quadrangle) throws RepositoryDataException;                                 //replace типо - должен брать quadrangle.getId() и искать Quadrangle с таким же айди, затем замена. если нету, тоже кидать ошибку

    List<Quadrangle> query(Specification<Quadrangle> specification);
    List<Quadrangle> query(Specification<Quadrangle> specification, Comparator<Quadrangle> comparator);
}
