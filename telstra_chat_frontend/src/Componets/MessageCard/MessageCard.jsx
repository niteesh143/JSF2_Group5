import React from 'react'

const MessageCard = ({ isReqUserMessage, content, username }) => {
  return (
    <div className={`py-2 px-2 rounded-md max-w-[50%] ${isReqUserMessage ? "self-start bg-white" : "self-end bg-[#d9fdd3]"}`}>
      {isReqUserMessage && <p className="text-sm font-bold mb-1">{username}</p>}
      <p>{content}</p>
    </div>
  )
}

export default MessageCard
