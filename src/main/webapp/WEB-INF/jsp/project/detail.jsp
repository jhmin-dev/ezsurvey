<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<main>
<div class="title">
	설문조사 상세
</div>
<article>
	<ul>
		<li>
			${responseDTO.title}
		</li>
		<li>
			${responseDTO.content}
		</li>
		<li>
			${responseDTO.userName}
		</li>
		<li>
			<label class="label-button">
				<input type="button" onclick="toggleBookmark()">
				<i class="bi bi-bookmark bookmark"></i>
			</label>
		</li>
	</ul>
</article>
<script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/js/detail.js"></script>
</main>