package io.ezsurvey.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ezsurvey.entity.question.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, CustomItemRepository {

}
