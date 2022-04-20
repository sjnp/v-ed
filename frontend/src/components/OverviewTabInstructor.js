import React from 'react'

const OverviewTabInstructor = ({ data }) => {

    const { instructorFirstname, instructorLastname, instructorPictureURI, biography, occupation } = data
    
    return (
        <div>
            <div>{ instructorFirstname ? instructorFirstname : 'Instructor firstname null' }</div>
            <div>{ instructorLastname ? instructorLastname : 'Instructor Lastname null' }</div>
            <div>{ instructorPictureURI ? instructorPictureURI : 'Instructor Picture URI null' }</div>
            <div>{ biography ? biography : 'Instructor biography null' }</div>
            <div>{ occupation ? occupation : 'Instructor occupation null' }</div>
        </div>
    )
}

export default OverviewTabInstructor