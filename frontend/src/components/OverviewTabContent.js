import React from 'react'

const OverviewTabContent = ({ data }) => {

    return (
        <div>
            <ul>
            {
                data?.map((chapter, indexChapter) => (
                    <li key={indexChapter}>
                        <span style={{ fontWeight: 'bold' }}>
                            {`Chapter ${indexChapter + 1}: ${chapter.name}`}
                        </span>
                        <ul>
                        {
                            chapter.sections.map((section, indexSection) => (
                                <li key={indexSection}>
                                    {`Section ${indexSection + 1}: ${section.name}`}
                                </li>
                            ))
                        }
                        </ul>
                    </li>
                ))
            }
            </ul>
        </div>
    )
}

export default OverviewTabContent