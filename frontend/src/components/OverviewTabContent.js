import React from 'react'

// component
import ContentPaper from './ContentPaper'

// Materail UI component
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

const OverviewTabContent = ({ data }) => {

    return (

        <Box minHeight={500} paddingTop={5} paddingLeft={20} paddingRight={20}>
        {
            data?.map((chapter, indexChapter) => {
                const label = `Chapter ${indexChapter + 1} : ${chapter.name}`

                let content = []
                content.push(chapter.sections?.map((section, indexSection) => (
                    <Box key={indexSection} whiteSpace={10} marginBottom={1} marginTop={1}>
                        <Typography variant='subtitle2' component='span'>
                            Section {indexSection + 1} :
                        </Typography>
                        <Typography variant='body2' component='span' paddingLeft={2}>
                            {section.name}
                        </Typography>
                    </Box>    
                )))

                return <ContentPaper key={indexChapter} label={label} content={content} />
            }) 
        }
        </Box>
    )
}

export default OverviewTabContent