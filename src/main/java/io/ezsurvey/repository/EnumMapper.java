package io.ezsurvey.repository;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.ezsurvey.dto.EnumDTO;
import io.ezsurvey.entity.EnumBase;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnumMapper { // enum을 View 레이어에서 사용하기 위해 DTO로 변환
	private Map<String, List<EnumDTO>> factory = new LinkedHashMap<>(); // HashMap과 달리 put()으로 입력된 key 순서 보장
	
	private <E extends EnumBase> List<EnumDTO> toEnumDTOs(Class<E> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants())
				.map(EnumDTO::new).collect(Collectors.toList());
	}
	
	public <E extends EnumBase> void put(String factoryKey, Class<E> enumClass) {
		factory.put(factoryKey, toEnumDTOs(enumClass));
	}
	
	public List<EnumDTO> get(String factoryKey) {
		return factory.get(factoryKey);
	}
	
	public Map<String, List<EnumDTO>> get(List<String> factoryKeys) {
		if(factoryKeys==null || factoryKeys.size()==0) {
			return new LinkedHashMap<>();
		}
		
		return factoryKeys.stream()
				.collect(Collectors.toMap(Function.identity(), factoryKey -> factory.get(factoryKey)));
	}
	
	public Map<String, List<EnumDTO>> getAll() {
		return factory;
	}
}
