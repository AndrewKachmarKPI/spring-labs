function toggleLikeCounter(likeIcon) {
  const likeCount = likeIcon.parentElement.querySelector(".like-count");
  const dislikeCount =
    likeIcon.parentElement.nextElementSibling.querySelector(".dislike-count");
  let likeValue = parseInt(likeCount.textContent);
  let dislikeValue = parseInt(dislikeCount.textContent);

  if (likeIcon.classList.contains("active")) {
    likeValue--;
    likeIcon.classList.remove("active");
  } else {
    likeValue++;
    likeIcon.classList.add("active");

    // Если иконка дизлайка была активной, уменьшаем её счёт и убираем активный класс
    if (
      likeIcon.parentElement.nextElementSibling
        .querySelector(".dislike-icon")
        .classList.contains("active")
    ) {
      dislikeValue--;
      likeIcon.parentElement.nextElementSibling
        .querySelector(".dislike-icon")
        .classList.remove("active");
    }
  }

  likeCount.textContent = likeValue;
  dislikeCount.textContent = dislikeValue;
}

function toggleDislikeCounter(dislikeIcon) {
  const dislikeCount =
    dislikeIcon.parentElement.querySelector(".dislike-count");
  const likeCount =
    dislikeIcon.parentElement.previousElementSibling.querySelector(
      ".like-count"
    );
  let dislikeValue = parseInt(dislikeCount.textContent);
  let likeValue = parseInt(likeCount.textContent);

  if (dislikeIcon.classList.contains("active")) {
    dislikeValue--;
    dislikeIcon.classList.remove("active");
  } else {
    dislikeValue++;
    dislikeIcon.classList.add("active");
 
    if (
      dislikeIcon.parentElement.previousElementSibling
        .querySelector(".like-icon")
        .classList.contains("active")
    ) {
      likeValue--;
      dislikeIcon.parentElement.previousElementSibling
        .querySelector(".like-icon")
        .classList.remove("active");
    }
  }

  likeCount.textContent = likeValue;
  dislikeCount.textContent = dislikeValue;
}


 