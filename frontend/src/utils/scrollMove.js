const topToBottom = () => {
    window.scroll({
        top: document.body.offsetHeight,
        left: 0, 
        behavior: 'smooth',
    })
}

const scrollMove = {
    topToBottom
}

export default scrollMove;