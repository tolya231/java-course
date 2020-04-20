package com.epam.repositories.impl;

import com.epam.dto.DogDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.repositories.DogDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateDao implements DogDao {

  private final SessionFactory sessionFactory;

  public HibernateDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public DogDto create(DogDto dog) {
    Long id = (Long) getCurrentSession().save(dog);
    DogDto dogDto = getCurrentSession().get(DogDto.class, id);
    if (dogDto != null) {
      return dogDto;
    } else {
      throw new RuntimeException("Dog creation failed");
    }
  }

  @Override
  public DogDto update(DogDto dog) {
    return (DogDto) getCurrentSession().merge(dog);
  }

  @Override
  public DogDto get(long id) {
    DogDto dogDto = getCurrentSession().get(DogDto.class, id);
    if (dogDto != null) {
      return dogDto;
    } else {
      throw new ResourceNotFoundException();
    }
  }

  @Override
  public void delete(long id) {
    Session session = getCurrentSession();
    DogDto dogDto = session.get(DogDto.class, id);
    if (dogDto != null) {
      session.delete(dogDto);
    } else {
      throw new ResourceNotFoundException();
    }
  }

  private Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }
}
