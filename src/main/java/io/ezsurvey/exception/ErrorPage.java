package io.ezsurvey.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorPage {
	DEFAULT_VIEW("/exception");
	
	public final String name;
}
