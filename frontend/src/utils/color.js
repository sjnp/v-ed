const getColorCommentType = (commentState) => {
    switch (commentState.toUpperCase()) {
        case 'OWNER': return '#76BACB'
        case 'STUDENT': return '#80BFA0'
        case 'INSTRUCTOR': return '#FF9C9C'
        default: throw 'ERROR!!! Comment state invalid.'
    }
}

const color = {
    getColorCommentType
}

export default color