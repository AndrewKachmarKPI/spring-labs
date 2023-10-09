function toggleLikeCounter(likeIcon) {
  const likeCount = likeIcon.parentElement.querySelector(".like-count");
  const dislikeIcon =
    likeIcon.parentElement.nextElementSibling.querySelector(".dislike-icon");
  const dislikeCount =
    dislikeIcon.parentElement.querySelector(".dislike-count");

  let likeValue = parseInt(likeCount.textContent);
  let dislikeValue = parseInt(dislikeCount.textContent);

  if (likeIcon.classList.contains("active")) {
    likeValue--;
    likeIcon.classList.remove("active");
  } else {
    likeValue++;
    likeIcon.classList.add("active");

    if (dislikeIcon.classList.contains("active")) {
      dislikeValue--;
      dislikeIcon.classList.remove("active");
    }
  }

  likeCount.textContent = likeValue;
  dislikeCount.textContent = dislikeValue;
}

function toggleDislikeCounter(dislikeIcon) {
  const dislikeCount =
    dislikeIcon.parentElement.querySelector(".dislike-count");
  const likeIcon =
    dislikeIcon.parentElement.previousElementSibling.querySelector(
      ".like-icon"
    );
  const likeCount = likeIcon.parentElement.querySelector(".like-count");

  let dislikeValue = parseInt(dislikeCount.textContent);
  let likeValue = parseInt(likeCount.textContent);

  if (dislikeIcon.classList.contains("active")) {
    dislikeValue--;
    dislikeIcon.classList.remove("active");
  } else {
    dislikeValue++;
    dislikeIcon.classList.add("active");

    if (likeIcon.classList.contains("active")) {
      likeValue--;
      likeIcon.classList.remove("active");
    }
  }

  likeCount.textContent = likeValue;
  dislikeCount.textContent = dislikeValue;
}
