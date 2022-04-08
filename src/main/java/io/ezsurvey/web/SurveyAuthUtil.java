package io.ezsurvey.web;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import io.ezsurvey.dto.survey.SurveyAuthDTO;
import io.ezsurvey.dto.user.SessionUser;
import io.ezsurvey.entity.survey.Visibility;
import io.ezsurvey.exception.DeletedSurveyException;
import io.ezsurvey.exception.EntityNotFoundException;
import io.ezsurvey.exception.InvalidSurveyOwnerException;
import io.ezsurvey.exception.InvalidSurveyStatusException;
import io.ezsurvey.exception.InvalidSurveyVisibilityException;

public class SurveyAuthUtil {
	public static void hasDetailAuthOrThrowException(SurveyAuthDTO authDTO, SessionUser sessionUser) {
		// 존재하지 않거나 삭제된 설문조사 번호로 접속한 경우 예외 발생
		existsOrThrowException(authDTO);

		// 전체 공개인 경우
		if(isPublic(authDTO)) return;	
		
		// 로그인한 사용자가 설문조사 생성자와 일치하는 경우
		if(sessionUser!=null && isOwner(authDTO, sessionUser.getUserId())) return;
		
		// 전체 공개도 아니고 생성자도 아닌 경우 예외 발생
		throw new InvalidSurveyVisibilityException();
	}
	
	public static void hasPreviewAuthOrThrowException(SurveyAuthDTO authDTO, String shared, SessionUser sessionUser) {
		// 존재하지 않거나 삭제된 설문조사 번호로 접속한 경우 예외 발생
		existsOrThrowException(authDTO);
		
		// 전체 공개이거나 올바른 shared값이 전달된 경우
		if(isPublic(authDTO) || isValidShared(authDTO, shared)) return;
		
		// 로그인한 사용자가 설문조사 생성자와 일치하는 경우
		if(sessionUser!=null && isOwner(authDTO, sessionUser.getUserId())) return;
		
		// 전체 공개도, 올바른 shared 값도, 생성자도 아닌 경우
		throw new InvalidSurveyVisibilityException();
	}
	
	public static void hasEditAuthOrThrowException(SurveyAuthDTO authDTO, Long userId) {
		// 존재하지 않거나 삭제된 설문조사 번호로 접속한 경우
		existsOrThrowException(authDTO);

		// 로그인한 사용자가 설문조사 생성자와 불일치하는 경우
		if(!isOwner(authDTO, userId)) throw new InvalidSurveyOwnerException();

		// 현재 시각이 배포 시작 시각 이후인 경우
		if(isDistributed(authDTO)) throw new InvalidSurveyStatusException();
	}
	
	private static void existsOrThrowException(SurveyAuthDTO authDTO) {
		if(isNotFound(authDTO)) { // 잘못된 설문조사 번호로 접속한 경우
			throw new EntityNotFoundException();
		}
		
		if(isDeleted(authDTO)) { // 설문조사가 삭제된 경우
			throw new DeletedSurveyException();
		}
	}
	
	private static boolean isNotFound(SurveyAuthDTO authDTO) {
		return authDTO==null;
	}
	
	private static boolean isDeleted(SurveyAuthDTO authDTO) {
		return authDTO.getVisibility()==Visibility.DELETED;
	}
	
	private static boolean isPublic(SurveyAuthDTO authDTO) {
		return authDTO.getVisibility()==Visibility.PUBLIC;
	}
	
	private static boolean isOwner(SurveyAuthDTO authDTO, Long userId) {
		return authDTO.getUserId().equals(userId); // 값만 비교해야 하므로 == 연산자가 아니라 equals()를 사용해야 함
	}
	
	private static boolean isDistributed(SurveyAuthDTO authDTO) {
		LocalDateTime distributed = authDTO.getDistributed();
		return distributed!=null && LocalDateTime.now().isAfter(distributed);
	}
	
	private static boolean isExpired(SurveyAuthDTO authDTO) {
		LocalDateTime expires = authDTO.getExpires();
		return expires!=null && LocalDateTime.now().isAfter(expires);
	}
	
	private static boolean isValidShared(SurveyAuthDTO authDTO, String shared) {
		return authDTO.getVisibility()==Visibility.LINK_ONLY && StringUtils.equals(shared, authDTO.getShared());
	}	
}
